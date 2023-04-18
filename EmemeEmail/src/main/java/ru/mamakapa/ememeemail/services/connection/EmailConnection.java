package ru.mamakapa.ememeemail.services.connection;

import ru.mamakapa.ememeemail.entities.ImapEmail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Store;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmailConnection {

    void connect(ImapEmail email) throws MessagingException;

    boolean isConnected(ImapEmail email);

    Optional<List<Message>> getNewLetters(ImapEmail email) throws MessagingException;

    Optional<List<Message>> getLastMessagesByCount(ImapEmail email, int count) throws MessagingException;

}
