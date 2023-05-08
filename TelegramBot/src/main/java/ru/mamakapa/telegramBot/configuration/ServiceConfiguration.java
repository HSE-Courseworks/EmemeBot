package ru.mamakapa.telegramBot.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "service")
public record ServiceConfiguration(
        String emailUrl
){
}
