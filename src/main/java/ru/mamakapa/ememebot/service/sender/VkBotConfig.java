package ru.mamakapa.ememebot.service.sender;

import org.springframework.beans.factory.annotation.Value;

public class VkBotConfig extends BotConfig{
    public VkBotConfig(@Value("${vk.bot.token}") String token, @Value("${vk.bot.groupId}") String groupId){
        this.setToken(token);
        this.setIdentificator(groupId);
    }
}
