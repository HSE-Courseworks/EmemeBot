package ru.mamakapa.ememebot.service.sender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
@Component
@PropertySource("application.properties")
public class VkBotConfig extends BotConfig{
    @Value("${vk.bot.response}")
    private String response;
    @Value("${vk.bot.secret}")
    private String secretPassword;

    public String getSecretPassword() {
        return secretPassword;
    }
    public VkBotConfig(@Value("${vk.bot.token}") String token, @Value("${vk.bot.groupId}") String groupId){
        this.setToken(token);
        this.setIdentificator(groupId);
    }
}
