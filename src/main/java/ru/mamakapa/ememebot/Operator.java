package ru.mamakapa.ememebot;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mamakapa.ememebot.config.ImapConfig;
import ru.mamakapa.ememebot.service.email.EmailLetter;
import ru.mamakapa.ememebot.service.email.EmailService;
import ru.mamakapa.ememebot.service.sender.Sender;
import ru.mamakapa.ememebot.service.sender.exceptions.SendMessageException;

import javax.mail.Message;
import java.util.List;

@Service
@Getter
public class Operator {
    @Autowired
    private Sender sender;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ImapConfig imapConfig;
    public void run(){
        try {
            emailService.getEmailConnection().connectToEmail(imapConfig);
            List<Message> messages = null;
            while (true){
               messages = emailService.getEmailConnection().getLastMessages(imapConfig,
                       emailService.getEmailConnection().checkUpdates(imapConfig));
               if (messages != null){
                   for (Message message : messages){
                       EmailLetter letter = emailService.getEmailCompiler().constructLetter(message);
                       sender.sendMessage(letter, 623783153);
                   }
               }
               messages = null;
               Thread.sleep(60000);
            }
        } catch (Exception e) {
            System.out.println("Error send message! With exception: " + e.getMessage());
        }
    }
}
