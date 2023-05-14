package ru.mamakapa.telegramBot.service;

import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mamakapa.ememeSenderFunctionality.bot.service.FileSender;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.io.FilenameUtils.getExtension;

public class TelegramFileSender implements FileSender {
    private final AbsSender absSender;

    public TelegramFileSender(AbsSender absSender) {
        this.absSender = absSender;
    }

    public TelegramFileSender(String botToken) {
        this(new DefaultAbsSender(new DefaultBotOptions(), botToken) {});
    }

    private final static Set<String> PHOTO_EXTENSIONS = new HashSet<>(Arrays.asList(
            "png",
            "jpg",
            "jpeg",
            "gif"
    ));

    private enum FileType{
        PHOTO, DOCUMENT
    }

    @Override
    public void send(long chatId, File file) {
        try {
            switch (getFileType(file)){
                case PHOTO -> sendPhoto(chatId, file);
                case DOCUMENT -> sendDocument(chatId, file);
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendPhoto(long chatId, File file) throws TelegramApiException {
        absSender.execute(new SendPhoto(String.valueOf(chatId), new InputFile(file)));
    }

    private void sendDocument(long chatId, File file) throws TelegramApiException {
        absSender.execute(new SendDocument(String.valueOf(chatId), new InputFile(file)));
    }

    private FileType getFileType(File file) {
        String extension = getExtension(file.getName()).toLowerCase();
        if(PHOTO_EXTENSIONS.contains(extension)){
            return FileType.PHOTO;
        }else {
            return FileType.DOCUMENT;
        }
    }
}
