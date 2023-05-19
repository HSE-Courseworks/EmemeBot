package ru.mamakapa.telegramBot.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws")
public record AWSConfig(
        String accessKey,
        String secretKey,
        String region,
        String endpoint,
        String bucketName
) {
}
