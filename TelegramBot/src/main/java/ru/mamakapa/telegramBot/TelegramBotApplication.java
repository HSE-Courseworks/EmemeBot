package ru.mamakapa.telegramBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.mamakapa.telegramBot.configuration.AWSConfig;
import ru.mamakapa.telegramBot.configuration.KafkaConfig;
import ru.mamakapa.telegramBot.configuration.ServiceConfiguration;
import ru.mamakapa.telegramBot.configuration.TelegramConfiguration;

@SpringBootApplication
@EnableConfigurationProperties({
        TelegramConfiguration.class,
        AWSConfig.class,
        ServiceConfiguration.class,
        KafkaConfig.class
})
public class TelegramBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(TelegramBotApplication.class, args);
    }
}
