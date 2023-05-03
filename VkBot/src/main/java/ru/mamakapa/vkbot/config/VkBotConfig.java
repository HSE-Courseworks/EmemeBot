package ru.mamakapa.vkbot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "vk.bot.config", ignoreUnknownFields = false)
public record VkBotConfig(
   String token,
   int groupId,
   Callback callback
) {
    public record Callback(
            String confirmationCode,
            String secret
    ){}
}
