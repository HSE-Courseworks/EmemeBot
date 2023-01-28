package ru.mamakapa.ememebot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mamakapa.ememebot.service.sender.*;

@Configuration
public class SenderConfig {
    @Value("${user.sender.type}")
    private String userSenderType;
    @Bean
    Sender getSender(@Autowired VkBotConfig vkBotConfig, @Autowired TelegramBotConfig telegramBotConfig){
        switch (userSenderType){
            case "vk":
                return new VkSender(vkBotConfig);
            case "telegram":
                return new TelegramSender(telegramBotConfig);
            default:
                throw new RuntimeException(userSenderType + " is not sender type!");
        }
    }
}
