package ru.mamakapa.ememeemail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.mamakapa.ememeemail.configurations.ApplicationConfig;

@Slf4j
@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(ApplicationConfig.class)
public class EmemeEmailApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(EmemeEmailApplication.class, args);
        var config = ctx.getBean(ApplicationConfig.class);
        log.info(config.toString());
    }
}
