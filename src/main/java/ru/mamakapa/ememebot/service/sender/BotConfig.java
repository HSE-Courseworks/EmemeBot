package ru.mamakapa.ememebot.service.sender;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BotConfig {
    private String token;
    private String identificator;
}
