package ru.mamakapa.ememebot.service.sender;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mamakapa.ememebot.service.email.EmailLetter;

@Setter
@Getter
public abstract class AbstractSender implements Sender {
    @Getter(AccessLevel.PROTECTED)
    private EmailLetter emailLetter;
    @Getter(AccessLevel.PROTECTED)
    private BotConfig botConfig;
    @Getter(AccessLevel.PROTECTED)
    private int recipientId;
}
