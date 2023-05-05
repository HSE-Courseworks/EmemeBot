package ru.mamakapa.ememeemail.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(String vkBotBaseUrl, String tgBotBaseUrl, FileSender fileSender) {
    public record FileSender(String vkToken, String tgToken){}
}
