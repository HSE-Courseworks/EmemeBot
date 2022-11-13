package ru.mamakapa.ememebot.service.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mamakapa.ememebot.config.ImapConfig;

import javax.mail.Folder;
import javax.mail.Store;

//Getters в интерфейс
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractEmailConnection implements EmailConnection{
    protected ImapConfig imapConfig;
}
