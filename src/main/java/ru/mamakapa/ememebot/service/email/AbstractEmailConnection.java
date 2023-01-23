package ru.mamakapa.ememebot.service.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.mamakapa.ememebot.config.ImapConfig;

@Getter
@Setter
@Service
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractEmailConnection implements EmailConnection{

    @Value("${mail.startLettersToShow}")
    private int startLettersToShow;
    protected ImapConfig imapConfig;
}
