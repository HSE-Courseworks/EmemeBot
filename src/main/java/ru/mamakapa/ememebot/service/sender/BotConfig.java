package ru.mamakapa.ememebot.service.sender;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public abstract class BotConfig {
    @Setter(AccessLevel.PROTECTED)
    private String token;
    @Setter(AccessLevel.PROTECTED)
    private String identificator;
}
