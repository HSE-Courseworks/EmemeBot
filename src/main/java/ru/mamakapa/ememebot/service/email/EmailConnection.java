package ru.mamakapa.ememebot.service.email;

import ru.mamakapa.ememebot.config.ImapConfig;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.List;

public interface EmailConnection {
    void connectToEmail(ImapConfig imapConfig) throws Exception;
    List<Message> getLastMessages(ImapConfig imapConfig, int mesCount) throws MessagingException;
    int checkUpdates(ImapConfig imapConfig) throws MessagingException;
    void closeConnection(ImapConfig imapConfig) throws MessagingException;
}
