package ru.mamakapa.telegramBot.service;

import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mamakapa.ememeSenderFunctionality.bot.service.FileSender;

import java.io.File;

public class TelegramFileSender implements FileSender {
    private final AbsSender absSender;

    public TelegramFileSender(AbsSender absSender) {
        this.absSender = absSender;
    }

    public TelegramFileSender(String botToken) {
        this(new DefaultAbsSender(new DefaultBotOptions(), botToken) {});
    }

    @Override
    public void send(long chatId, File file) {
        try {
            absSender.execute(new SendDocument(String.valueOf(chatId), new InputFile(file)));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
