package ru.mamakapa.ememebot;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import ru.mamakapa.ememebot.config.ImapConfig;
import ru.mamakapa.ememebot.service.email.*;

import javax.mail.Message;
import java.io.File;


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
        Assert.assertSame(startLettersToShow, n);
    }

    @Test
    public void compileTest() throws Exception {
        emailService.getEmailConnection().connectToEmail(imapConfig);
        Message message = imapConfig.getInbox().getMessage(4);
        EmailLetter letter = emailService.getEmailCompiler().constructLetter(message);
        Assert.assertEquals(letter.getEnvelope(), "От: Ememe Bot <ememebot@yandex.ru> \n" +
                                                        "Тема: 4th Test\n" +
                                                        "Отправленно: Thu Jan 26 17:34:00 MSK 2023\n");

        Assert.assertEquals(letter.getBodyPart(), "\n" +
                                                        "Вложение: lab01-netIntro.zip\n" +
                                                        "Вложение: RASPISANIE_2022-2023_-_IMIKN_-_3_MODULJH_-_BAK_-_PI.xlsx\n");

        for (String filePath : letter.getAttachmentFilePaths()){
            File file = new File(filePath);
            Assert.assertTrue(file.exists());
        }
        for (String filePath : letter.getHtmlFilePaths()){
            File file = new File(filePath);
            Assert.assertTrue(file.exists());
        }

        Assert.assertTrue(emailService.deleteLetter(letter));
        emailService.getEmailConnection().closeConnection(imapConfig);
    }
}
