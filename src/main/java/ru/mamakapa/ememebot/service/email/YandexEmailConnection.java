package ru.mamakapa.ememebot.service.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.mamakapa.ememebot.config.ImapConfig;

import javax.mail.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Getter
@Setter
@Service
public class YandexEmailConnection extends AbstractEmailConnection {
    @Override
    public void connectToEmail(ImapConfig imapConfig) throws Exception {
        Properties prop = new Properties();
        this.imapConfig = imapConfig;

        prop.setProperty("mail.user", imapConfig.getUsername());
        prop.setProperty("mail.password", imapConfig.getPassword());
        prop.setProperty("mail.host", imapConfig.getHost());
        prop.setProperty("mail.store.protocol", "imaps");

        Store store = Session.getInstance(prop).getStore();
        store.connect(imapConfig.getHost(), imapConfig.getUsername(), imapConfig.getPassword());
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        imapConfig.setStore(store);
        imapConfig.setInbox(inbox);
        imapConfig.setConnected(true);
        imapConfig.setMessageCount(inbox.getMessageCount());
    }

    @Override
    public List<Message> getLastMessages(ImapConfig imapConfig, int mesCount) throws MessagingException {
        Folder inbox = imapConfig.getInbox();
        if (imapConfig.isConnected() && inbox != null && inbox.isOpen()) {
            List<Message> messages = new ArrayList<>();
            for (int i = 0; i < mesCount; ++i) {
                Message message = inbox.getMessage(inbox.getMessageCount() - i);
                messages.add(message);
            }
            return messages;
        }
        return null;
    }

    @Override
    public int checkUpdates(ImapConfig imapConfig) throws MessagingException {
        Folder inbox = imapConfig.getInbox();
        if (imapConfig.isConnected() && inbox != null && inbox.isOpen()) {
            int serverMessageCount = inbox.getMessageCount();
            int localMessageCount = imapConfig.getMessageCount();
            imapConfig.setMessageCount(serverMessageCount);

            return serverMessageCount - localMessageCount;
        }
        else return -1;
    }

    @Override
    public void closeConnection(ImapConfig imapConfig) throws MessagingException {
        imapConfig.getInbox().close(false);
        imapConfig.getStore().close();
        imapConfig.setConnected(false);
    }
}
