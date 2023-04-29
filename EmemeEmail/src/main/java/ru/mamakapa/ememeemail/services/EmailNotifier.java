package ru.mamakapa.ememeemail.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.mamakapa.ememeemail.DTOs.requests.LetterContent;
import ru.mamakapa.ememeemail.entities.BotUser;
import ru.mamakapa.ememeemail.entities.ImapEmail;
import ru.mamakapa.ememeemail.services.compiler.Compiler;
import ru.mamakapa.ememeemail.services.compiler.CompilerImpl;
import ru.mamakapa.ememeemail.services.compiler.EmailLetter;
import ru.mamakapa.ememeemail.services.connection.EmailConnection;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EmailNotifier {
    final private ImapEmailService emailService;
    final private BotUserService userService;
    final private EmailConnection emailConnection;
    final private VkBotClient vkBotClient;
    final private TgBotClient tgBotClient;
    final private Compiler compiler = new CompilerImpl();
    final static private int UPDATE_CHECK_INTERVAL = 30000;

    public EmailNotifier(ImapEmailService emailService, BotUserService userService, EmailConnection emailConnection, VkBotClient vkBotClient, TgBotClient tgBotClient) {
        this.emailService = emailService;
        this.userService = userService;
        this.emailConnection = emailConnection;
        this.vkBotClient = vkBotClient;
        this.tgBotClient = tgBotClient;
    }

    @Scheduled(fixedDelay = UPDATE_CHECK_INTERVAL)
    public void checkUpdateAndNotify(){
        try {
            var emailToCheck = emailService.getLatestCheckedEmail();
            connectToEmail(emailToCheck);

            emailConnection.getNewLetters(emailToCheck).ifPresent((l) -> {
                var processedMessages = processNewMessages(l);
                sendLettersToUsers(emailToCheck, processedMessages);
            });
        }catch (Exception ex){
            log.info(ex.getMessage());
        }
    }

    private void connectToEmail(ImapEmail email) throws MessagingException {
        if (!emailConnection.isConnected(email)) {
            emailConnection.connect(email);
        }
    }

    private List<EmailLetter> processNewMessages(List<Message> messages) {
        List<EmailLetter> letters = new ArrayList<>();
        try {
            for (var mes : messages){
                letters.add(compiler.compile(mes));
            }
        } catch (Exception ignored){}
        return letters;
    }

    private void sendLettersToUsers(ImapEmail emailInfo, List<EmailLetter> letters){
        List<BotUser> users = emailService.getAllSubscribedUsersForEmail(emailInfo.getEmail());
        for (var user : users){
            for (var letter : letters){
                LetterContent content = getLetterContent(letter);
                sendUpdate(user, content);
            }
        }
    }

    private LetterContent getLetterContent(EmailLetter letter){
        return  LetterContent.builder()
                .messageContent(letter.getEnvelope() + "\n" + letter.getBodyPart())
                .fileLinks(uploadFilesAndGetLinks(letter.getFiles()))
                .build();
    }

    private List<String> uploadFilesAndGetLinks(List<File> files){
        return new ArrayList<>();
    }

    private void sendUpdate(BotUser user, LetterContent content){
        switch (user.getMessengerType()){
            case TG -> tgBotClient.sendUpdateToTgBot(user.getChatId(), content);
            case VK -> vkBotClient.sendUpdateToVkBot(user.getChatId(), content);
        }
    }
}
