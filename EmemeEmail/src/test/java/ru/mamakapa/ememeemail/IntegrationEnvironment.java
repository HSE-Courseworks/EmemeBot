package ru.mamakapa.ememeemail;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Testcontainers
public class IntegrationEnvironment {
    @Container
    static final JdbcDatabaseContainer<?> DB_CONTRAINER;

    static {
        DB_CONTRAINER = new PostgreSQLContainer<>("postgres:15-alpine")
                .withDatabaseName("emailService")
                .withUsername("postgres_test")
                .withPassword("ememebot")
                .withUrlParam("stringtype", "unspecified");
        DB_CONTRAINER.start();

        Startables.deepStart(DB_CONTRAINER).thenAccept((none) -> migrateLiquibase(DB_CONTRAINER));
    }

    private static void migrateLiquibase(JdbcDatabaseContainer<?> container){
        Path masterMigration = new File(".").toPath()
                .toAbsolutePath()
                .getParent()
                .resolve("main")
                .resolve("resources")
                .resolve("db")
                .resolve("migrations");

        try {
            Connection connection = DriverManager.getConnection(container.getJdbcUrl(),
                    container.getUsername(), container.getPassword());

            PostgresDatabase pgDb = new PostgresDatabase();
            pgDb.setConnection(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase("master.xml", new DirectoryResourceAccessor(masterMigration), pgDb);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (SQLException | FileNotFoundException | LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }

    @DynamicPropertySource
    static void springData(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", DB_CONTRAINER::getJdbcUrl);
        registry.add("spring.datasource.username", DB_CONTRAINER::getUsername);
        registry.add("spring.datasource.password", DB_CONTRAINER::getPassword);
    }
}
