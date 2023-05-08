package ru.mamakapa.ememeemail.services;

import lombok.RequiredArgsConstructor;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailNotifier {
    final private static Path FILE_SAVING_PATH = Paths.get("EmemeEmail/src/main/resources/savedir").toAbsolutePath();
    final private ImapEmailService emailService;
    final private EmailConnection emailConnection;
    final private VkBotClient vkBotClient;
    final private TgBotClient tgBotClient;
    final private Compiler compiler = new CompilerImpl(FILE_SAVING_PATH);
    final static private int UPDATE_CHECK_INTERVAL = 30000;

    final FileUploader fileUploader;

    @Scheduled(fixedDelay = UPDATE_CHECK_INTERVAL)
    public void checkUpdateAndNotify(){
        try {
            var emailToCheck = emailService.getLatestCheckedEmail();
            log.info("Checking for updates of " + emailToCheck.getEmail());
            connectToEmail(emailToCheck);

            checkForMessagesSendThemAndGetLastMessageTime(emailToCheck)
                    .ifPresent(emailToCheck::setLastMessageTime);

            emailToCheck.setLastChecked(Timestamp.from(Instant.now()));
            emailService.patch(emailToCheck);
        }catch (Exception ex){
            log.info(ex.getMessage());
        }
    }

    private Optional<Timestamp> checkForMessagesSendThemAndGetLastMessageTime(ImapEmail emailToCheck) throws MessagingException {
        var optionalLetters = emailConnection.getNewLetters(emailToCheck);
        if (optionalLetters.isPresent()){
            var l = optionalLetters.get();
            log.info("{} letters were found!", l.size());
            var processedMessages = processNewMessages(l);
            sendLettersToUsers(emailToCheck, processedMessages);
            return Optional.of(Timestamp.from(l.get(l.size()-1).getSentDate().toInstant()));
        }
        else return Optional.empty();
    }

    private void connectToEmail(ImapEmail email) throws MessagingException {
        if (!emailConnection.isConnected(email)) {
            log.info("connecting");
            emailConnection.connect(email);
        }
    }

    private List<EmailLetter> processNewMessages(List<Message> messages) {
        log.info("processing {} messages", messages.size());
        List<EmailLetter> letters = new ArrayList<>();
        try {
            for (var mes : messages){
                letters.add(compiler.compile(mes));
            }
        } catch (Exception ignored){}
        log.info("{} letters was processed", letters.size());
        return letters;
    }

    private void sendLettersToUsers(ImapEmail emailInfo, List<EmailLetter> letters){
        List<BotUser> users = emailService.getAllSubscribedUsersForEmail(emailInfo.getEmail());
        log.info("sending {} letters to {} users", letters.size(), users.size());
        for (var letter : letters){
            LetterContent content = getLetterContent(letter);
            for (var user : users){
                sendUpdate(user, content);
                sendFiles(user, letter.getFiles());
            }
            compiler.deleteLetterFiles(letter);
        }
    }

    private LetterContent getLetterContent(EmailLetter letter){
        return LetterContent.builder()
                .messageContent(letter.getEnvelope() + "\n" + letter.getBodyPart())
                .build();
    }

    private void sendFiles(BotUser user, List<File> files){
        log.info("uploading files");
        for (var f : files){
            try {
                fileUploader.uploadFileToMessenger(f, user.getChatId(), user.getMessengerType());
            } catch (RuntimeException exception){
                log.info("Error sending file to user {} {}\nexception: {}",
                        user.getChatId(), user.getMessengerType(), exception.getMessage());
            }
        }
        log.info("files were uploaded");
    }

    private void sendUpdate(BotUser user, LetterContent content){
        log.info("Sending update to {} with mesType = {}", user.getChatId(), user.getMessengerType());
        try {
            switch (user.getMessengerType()){
                case TG -> tgBotClient.sendUpdateToTgBot(user.getChatId(), content);
                case VK -> vkBotClient.sendUpdateToVkBot(user.getChatId(), content);
            }
        } catch (RuntimeException exception){
            log.info("Exception in sendUpdate method! Message: {}", exception.getMessage());
        }
    }
}