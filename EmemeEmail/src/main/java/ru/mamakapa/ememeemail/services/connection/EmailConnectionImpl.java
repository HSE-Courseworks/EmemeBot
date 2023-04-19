package ru.mamakapa.ememeemail.services.connection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mamakapa.ememeemail.entities.ImapEmail;

import javax.mail.*;
import java.sql.Timestamp;
import java.util.*;

@Slf4j
@Service
public class EmailConnectionImpl implements EmailConnection {

    Map<String, Store> loginConnections = new HashMap<>();

    @Override
    public void connect(ImapEmail email) throws MessagingException {
        Properties prop = new Properties();

        prop.setProperty("mail.user", email.getEmail());
        prop.setProperty("mail.password", email.getAppPassword());
        prop.setProperty("mail.host", email.getHost());
        prop.setProperty("mail.store.protocol", "imaps");

        log.info("Connecting to Imap Server with email: " + email.getEmail());

        Store store = Session.getInstance(prop).getStore();
        store.connect(email.getHost(), email.getEmail(), email.getAppPassword());

        log.info("Connected");

        if (!loginConnections.containsKey(email.getEmail())){
            loginConnections.put(email.getEmail(), store);
        }
    }

    @Override
    public boolean isConnected(ImapEmail email) {
        return loginConnections.containsKey(email.getEmail()) &&
                loginConnections.get(email.getEmail()).isConnected();
    }

    @Override
    public Optional<List<Message>> getNewLetters(ImapEmail email) throws MessagingException {
        var optMessages = getLastMessagesByCount(email, LAST_MESSAGES_TO_CHECK_COUNT);
        if (optMessages.isPresent()) {
            var messages = optMessages.get();
            ArrayList<Message> newMessages = new ArrayList<>();
            for (int i = messages.size() - 1; i >= 0; --i) {
                if (isNewMessage(messages.get(i).getSentDate(), email.getLastMessageTime())) {
                    newMessages.add(messages.get(i));
                }
            }
            return Optional.of(newMessages);
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Message>> getLastMessagesByCount(ImapEmail email, int count) throws MessagingException {
        if (isConnected(email)) {
            var store = loginConnections.get(email.getEmail());
            var inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            var inboxMessageCount = inbox.getMessageCount();
            ArrayList<Message> messages = new ArrayList<>();
            for (int i = 0; i < count; ++i){
                messages.add(inbox.getMessage(inboxMessageCount-i));
            }
            return Optional.of(messages);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Message> getMessageByNumber(ImapEmail email, int n) throws MessagingException {
        if (isConnected(email)){
            var store = loginConnections.get(email.getEmail());
            var inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            return Optional.of(inbox.getMessage(n));
        }
        return Optional.empty();
    }

    @Override
    public void closeConnection(ImapEmail email) throws MessagingException {
        var store = loginConnections.get(email.getEmail());
        store.close();
    }

    private boolean isNewMessage(Date messageSentDate, Timestamp lastMessageTime){
        Timestamp messageStamp = getTimestampFromDate(messageSentDate);
        return messageStamp.after(lastMessageTime);
    }

    private Timestamp getTimestampFromDate(Date date){
        return Timestamp.from(date.toInstant());
    }
}
