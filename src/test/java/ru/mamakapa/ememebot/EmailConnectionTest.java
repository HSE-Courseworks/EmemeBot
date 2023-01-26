package ru.mamakapa.ememebot;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.mamakapa.ememebot.config.ImapConfig;
import ru.mamakapa.ememebot.service.email.*;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;


@SpringBootTest
public class EmailConnectionTest {
    @Autowired
    private ImapConfig imapConfig;

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
        Assert.assertNotSame(0, n);
    }

    @Test
    public void compileTest() throws Exception {
        emailConnection.connectToEmail(imapConfig);
        EmailLetter letter = emailCompiler.constructLetter(emailConnection.getLastMessages(imapConfig, 1).get(0));
        System.out.println(letter.getEnvelope());
        System.out.println(letter.getBodyPart());
        emailConnection.closeConnection(imapConfig);
    }
}
