package ru.mamakapa.telegramBot.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka")
public record KafkaConfig(
        String serverUrl,
        String topicName
) {
}
