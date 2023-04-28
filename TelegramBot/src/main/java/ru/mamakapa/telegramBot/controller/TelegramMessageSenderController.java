package ru.mamakapa.telegramBot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mamakapa.ememeSenderFunctionality.bot.service.MessageSender;

@RestController
@RequestMapping("tgbot/updates")
public class TelegramMessageSenderController {
    private final MessageSender<Integer, String> messageSender;
    public TelegramMessageSenderController(MessageSender<Integer, String> messageSender) {
        this.messageSender = messageSender;
    }

    @PostMapping("{chatId}")
    public void getUpdates(

    ){

    }
}
