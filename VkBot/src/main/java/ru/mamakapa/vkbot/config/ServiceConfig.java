package ru.mamakapa.vkbot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("service")
public record ServiceConfig(
        String emailUrl
) {
}
