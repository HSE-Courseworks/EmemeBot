package ru.mamakapa.ememeemail.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(String vkBotBaseUrl, String tgBotBaseUrl, FileSender fileSender, TestConfig test) {
    public record FileSender(String vkToken, String tgToken){}

    public record TestConfig(String email, String host, String password){}
}
