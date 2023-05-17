package ru.mamakapa.ememeemail.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(String vkBotBaseUrl, String tgBotBaseUrl, TestConfig test) {

    public record TestConfig(String email, String host, String password){}
}
