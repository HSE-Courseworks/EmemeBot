package ru.mamakapa.ememeemail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.mamakapa.ememeemail.configurations.ApplicationConfig;

@SpringBootApplication
@Slf4j
@EnableConfigurationProperties(ApplicationConfig.class)
public class EmemeEmailApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(EmemeEmailApplication.class, args);
        var config = ctx.getBean(ApplicationConfig.class);
        log.info(config.test());
    }
}
