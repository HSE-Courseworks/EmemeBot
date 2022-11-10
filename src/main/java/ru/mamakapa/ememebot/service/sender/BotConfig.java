package ru.mamakapa.ememebot.service.sender;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Setter
public abstract class BotConfig {
    private String token;
    private String identificator;
}
