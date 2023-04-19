package ru.mamakapa.ememeemail.services.connection;

import ru.mamakapa.ememeemail.entities.ImapEmail;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;

public interface EmailConnection{

    int LAST_MESSAGES_TO_CHECK_COUNT = 15;

    void connect(ImapEmail email) throws MessagingException;

    boolean isConnected(ImapEmail email);

    Optional<List<Message>> getNewLetters(ImapEmail email) throws MessagingException;

    Optional<List<Message>> getLastMessagesByCount(ImapEmail email, int count) throws MessagingException;

    Optional<Message> getMessageByNumber(ImapEmail email, int n) throws MessagingException;

    void closeConnection(ImapEmail email) throws MessagingException;

}
