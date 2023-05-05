package ru.mamakapa.ememeemail.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
<<<<<<< HEAD
public record ApplicationConfig(String test, String vkBotBaseUrl, String tgBotBaseUrl, Dropbox dropbox) {
    public record Dropbox(String token, String clientId, String clientSecret){}
=======
public record ApplicationConfig(String test) {
>>>>>>> telegramBot
}
