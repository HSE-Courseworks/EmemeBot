package ru.mamakapa.ememebot.service.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mamakapa.ememebot.service.email.EmailLetter;
import ru.mamakapa.ememebot.service.sender.exceptions.SendMessageException;

import java.io.File;

@Slf4j
@Component("telegram")
public class TelegramSender extends AbstractSender {
    private DefaultBotOptions defaultBotOptions = new DefaultBotOptions();
    private DefaultAbsSender absSender;
    private static final int MAX_MESSAGE_LENGTH = 4096;

    @Autowired
    public TelegramSender(TelegramBotConfig config) {
        this.absSender = new DefaultAbsSender(defaultBotOptions) {
            @Override
            public String getBotToken() {
                return config.getToken();
            }
        };
    }

    @Override
    public void sendMessage(EmailLetter emailLetter, int recipientId) throws SendMessageException {
        log.info("start to send message");
        try {
            if (emailLetter.getEnvelope().length() + emailLetter.getBodyPart().length() <= MAX_MESSAGE_LENGTH) {
                absSender.execute(SendMessage.builder().
                        text(emailLetter.getEnvelope() + emailLetter.getBodyPart())
                        .chatId(String.valueOf(recipientId))
                        .build());
            }else {
                log.info("Splitting letter...");
                int currentMessageStartPosition = 0;
                log.info("Sending envelope");
                while (currentMessageStartPosition<emailLetter.getEnvelope().length()){
                    absSender.execute(SendMessage.builder()
                            .chatId(String.valueOf(recipientId))
                            .text(emailLetter.getEnvelope().
                                    substring(currentMessageStartPosition,
                                    Math.min(currentMessageStartPosition + MAX_MESSAGE_LENGTH, emailLetter.getEnvelope().length())))
                            .build());
                    currentMessageStartPosition += MAX_MESSAGE_LENGTH;
                }
                log.info("Sending bodyPart");
                currentMessageStartPosition = 0;
                while (currentMessageStartPosition<emailLetter.getBodyPart().length()){
                    absSender.execute(SendMessage.builder()
                            .chatId(String.valueOf(recipientId))
                            .text(emailLetter.getBodyPart().
                                    substring(currentMessageStartPosition,
                                    Math.min(currentMessageStartPosition + MAX_MESSAGE_LENGTH, emailLetter.getBodyPart().length())))
                            .build());
                    currentMessageStartPosition += MAX_MESSAGE_LENGTH;
                }
            }
            log.info("Sending html files");
            if (emailLetter.getHtmlFilePaths().size() != 0) {
                for (String path : emailLetter.getHtmlFilePaths()) {
                    absSender.execute(SendPhoto.builder().
                            photo(new InputFile(new File(path)))
                            .chatId(String.valueOf(recipientId))
                            .build());
                }
            }
            log.info("Sending attachment files");
            if (emailLetter.getAttachmentFilePaths().size() != 0) {
                for (String path : emailLetter.getAttachmentFilePaths()){
                    absSender.execute(SendDocument.builder()
                            .document(new InputFile(new File(path)))
                            .chatId(String.valueOf(recipientId))
                            .build());
                }
            }
        } catch (TelegramApiException e) {
            throw new SendMessageException();
        }
    }
}