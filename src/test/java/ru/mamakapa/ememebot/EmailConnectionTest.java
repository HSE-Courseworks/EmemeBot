package ru.mamakapa.ememebot;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import ru.mamakapa.ememebot.config.ImapConfig;
import ru.mamakapa.ememebot.entities.EmailMessage;
import ru.mamakapa.ememebot.repositories.EmailMessageRepo;
import ru.mamakapa.ememebot.service.email.*;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public class EmailConnectionTest {

    @Value("${mail.startLettersToShow}")
    private int startLettersToShow;
    @Autowired
    private ImapConfig imapConfig;

    @Autowired
    private EmailMessageRepo emailMessageRepo;


    @Qualifier("EmailStableConnection")
    @Autowired
    public EmailConnection emailConnection;
    @Autowired
    public EmailCompiler emailCompiler;

    @Test
    public void connectToEmailTest() throws Exception {
        emailConnection.connectToEmail(imapConfig);
        emailConnection.closeConnection(imapConfig);
    }

    @Test
    public void getLastMessagesTest() throws Exception {
        emailConnection.connectToEmail(imapConfig);
        Assert.assertNotNull(emailConnection.getLastMessages(imapConfig,1));
        emailConnection.closeConnection(imapConfig);
    }

    @Test
    public void checkUpdates() throws Exception {
        emailConnection.connectToEmail(imapConfig);
        int n = emailConnection.checkUpdates(imapConfig);
        emailConnection.closeConnection(imapConfig);
        Assert.assertEquals(startLettersToShow, n);
    }

    @Test
    public void compileTest() throws Exception {
        emailConnection.connectToEmail(imapConfig);
        EmailLetter letter = emailCompiler.constructLetter(emailConnection.getLastMessages(imapConfig, 1).get(0));
        System.out.println(letter.getEnvelope());
        System.out.println(letter.getBodyPart());
        emailConnection.closeConnection(imapConfig);
    }

    @Test
    public void dataBaseSavingAndDeletingTest() throws Exception {
        emailConnection.connectToEmail(imapConfig);
        List<Message> messages =  emailConnection.getLastMessages(imapConfig, 3);
        List<String> ids = new ArrayList<>();
        for (Message message : messages){
            String id = ((MimeMessage)message).getMessageID();
            ids.add(id);
            emailMessageRepo.save(new EmailMessage(id, message.getSentDate()));
        }
        for (String id : ids){
            EmailMessage emailMessage = emailMessageRepo.getEmailMessagesByImapEmailId(id).get(0);
            Assert.assertTrue(emailMessage.getImapEmailId().equals(id));
            emailMessageRepo.deleteAllByImapEmailId(id);
        }

        emailConnection.closeConnection(imapConfig);
    }

    @Test
    public void dbGetTopTest() throws Exception {
        int count = 3;
        emailConnection.connectToEmail(imapConfig);
        List<Message> messages =  emailConnection.getLastMessages(imapConfig, count);
        List<String> ids = new ArrayList<>();
        for (Message message : messages){
            String id = ((MimeMessage)message).getMessageID();
            ids.add(id);
            emailMessageRepo.save(new EmailMessage(id, message.getSentDate()));
        }
        for (int i = 0; i < count; ++i){
            EmailMessage emailMessage = emailMessageRepo.getTopByOrderByIdAsc();
            Assert.assertTrue(emailMessage.getImapEmailId().equals(ids.get(i)));
            emailMessageRepo.deleteById(emailMessage.getId());
        }
    }
}
