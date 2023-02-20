package ru.mamakapa.ememebot;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import ru.mamakapa.ememebot.config.ImapConfig;
import ru.mamakapa.ememebot.service.email.*;


@SpringBootTest
public class EmailConnectionTest {
    @Value("${mail.startLettersToShow}")
    private int startLettersToShow;
    @Autowired
    private ImapConfig imapConfig;

    @Autowired
    EmailService emailService;
    @Test
    public void connectToEmailTest() throws Exception {
        emailService.getEmailConnection().connectToEmail(imapConfig);
        emailService.getEmailConnection().closeConnection(imapConfig);
    }

    @Test
    public void getLastMessagesTest() throws Exception {
        emailService.getEmailConnection().connectToEmail(imapConfig);
        Assert.assertNotNull(emailService.getEmailConnection().getLastMessages(imapConfig,5));
        emailService.getEmailConnection().closeConnection(imapConfig);
    }

    @Test
    public void checkUpdates() throws Exception {
        emailService.getEmailConnection().connectToEmail(imapConfig);
        int n = emailService.getEmailConnection().checkUpdates(imapConfig);
        emailService.getEmailConnection().closeConnection(imapConfig);
        Assert.assertNotSame(startLettersToShow, n);
    }

    @Test
    public void compileTest() throws Exception {
        emailService.getEmailConnection().connectToEmail(imapConfig);
        EmailLetter letter = emailService.getEmailCompiler().constructLetter(emailService.getEmailConnection().getLastMessages(imapConfig, 1).get(0));
        System.out.println(letter.getEnvelope());
        System.out.println(letter.getBodyPart());
        emailService.deleteLetter(letter);
        emailService.getEmailConnection().closeConnection(imapConfig);
    }
}
