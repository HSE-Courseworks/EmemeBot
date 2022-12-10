package ru.mamakapa.ememebot.service.sender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("application.properties")
public class TelegramBotConfig extends BotConfig{
    public TelegramBotConfig(@Value("${telegram.bot.name}") String name, @Value("${telegram.bot.token}") String token){
        this.setIdentificator(name);
        this.setToken(token);
    }
}
