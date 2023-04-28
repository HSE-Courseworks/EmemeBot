package ru.mamakapa.telegramBot.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bot.telegram", ignoreUnknownFields = false)
public record TelegramConfiguration(
        String token
) {}
