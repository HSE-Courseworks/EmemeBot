package ru.mamakapa.ememebot.service.email;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.mamakapa.ememebot.config.ImapConfig;
import ru.mamakapa.ememebot.entities.EmailMessage;
import ru.mamakapa.ememebot.repositories.EmailMessageRepo;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Stack;

@Service
@Getter
@Setter
@Qualifier("EmailStableConnection")
@Slf4j
public class EmailStableConnection extends AbstractEmailConnection{

    final private EmailMessageRepo emailMessageRepo;

    public EmailStableConnection(EmailMessageRepo emailMessageRepo) {
        this.emailMessageRepo = emailMessageRepo;
    }

    @Override
    public void connectToEmail(ImapConfig imapConfig) throws Exception {
        Properties prop = new Properties();
        this.imapConfig = imapConfig;

        prop.setProperty("mail.user", imapConfig.getUsername());
        prop.setProperty("mail.password", imapConfig.getPassword());
        prop.setProperty("mail.host", imapConfig.getHost());
        prop.setProperty("mail.store.protocol", "imaps");

        log.info("Connecting to Imap Server with email: " + imapConfig.getUsername());

        Store store = Session.getInstance(prop).getStore();
        store.connect(imapConfig.getHost(), imapConfig.getUsername(), imapConfig.getPassword());

        log.info("Connected. Opening inbox folder");

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        imapConfig.setStore(store);
        imapConfig.setInbox(inbox);
        imapConfig.setConnected(true);

        Message message = inbox.getMessage(inbox.getMessageCount() - getStartLettersToShow());
        log.info("Saving " + getStartLettersToShow() + " last messages in DataBase");
        EmailMessage firstMes = new EmailMessage(((MimeMessage) message).getMessageID(), message.getSentDate());
        if (!emailMessageRepo.existsByImapEmailId(firstMes.getImapEmailId())) {
            emailMessageRepo.save(firstMes);
            setStartLettersToShow(0);
        }
    }

    @Override
    public List<Message> getLastMessages(ImapConfig imapConfig, int mesCount) throws MessagingException {
        Folder inbox = imapConfig.getInbox();
        if (mesCount < 0) throw new MessagingException("Exception in getLastMessages! " +
                "Inappropriate messages' count: " + mesCount);
        if (isConnected(imapConfig)) {
            log.info("Searching for " + mesCount + " messages of " + imapConfig.getUsername());
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
        if (isConnected(imapConfig)) {
            if (getStartLettersToShow() != 0){
                List<Message> messages = getLastMessages(imapConfig, getStartLettersToShow());
                for (int i = getStartLettersToShow()-1; i >= 0; --i ){
                    Message mes = messages.get(i);
                    EmailMessage emailMessage = new EmailMessage(((MimeMessage)mes).getMessageID(), mes.getSentDate());
                    if (!emailMessageRepo.existsByImapEmailId(emailMessage.getImapEmailId())) {
                        emailMessageRepo.save(emailMessage);
                    }
                }
                int resCount = getStartLettersToShow();
                setStartLettersToShow(0);
                return resCount;
            }

            EmailMessage lastMessageInDB = emailMessageRepo.getTopByOrderBySendDateDesc();
            Stack<EmailMessage> messagesStack = new Stack<>();
            int mesCount = inbox.getMessageCount();
            while (true){
                Message message = inbox.getMessage(mesCount--);
                if (message.getSentDate().compareTo(lastMessageInDB.getSendDate()) >= 0){
                    if (((MimeMessage)message).getMessageID().equals(lastMessageInDB.getImapEmailId())){
                        break;
                    }
                    messagesStack.push(new EmailMessage(((MimeMessage)message).getMessageID(), message.getSentDate()));
                }
                else break;
            }

            int newMessagesCount = messagesStack.size();
            while (!messagesStack.isEmpty()){
                EmailMessage emailMessage = messagesStack.pop();
                if (!emailMessageRepo.existsByImapEmailId(emailMessage.getImapEmailId())){
                    emailMessageRepo.save(messagesStack.pop());
                }
            }
            return newMessagesCount;
        }
        return -1;
    }

    @Override
    public void closeConnection(ImapConfig imapConfig) throws MessagingException {
        log.info("Closing connection of " + imapConfig.getUsername());
        imapConfig.getInbox().close(false);
        imapConfig.getStore().close();
        imapConfig.setConnected(false);
    }

    @Override
    public boolean isConnected(ImapConfig imapConfig) throws MessagingException {
        if (imapConfig.getInbox() != null) {
            imapConfig.setConnected(imapConfig.getInbox().isOpen());
            return imapConfig.isConnected();
        }
        else return false;
    }
}
