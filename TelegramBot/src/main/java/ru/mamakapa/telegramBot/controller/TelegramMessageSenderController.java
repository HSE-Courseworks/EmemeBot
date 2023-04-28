package ru.mamakapa.telegramBot.controller;

import org.springframework.web.bind.annotation.*;
import ru.mamakapa.ememeSenderFunctionality.bot.dto.EmailLetterRequest;
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
            @RequestBody EmailLetterRequest emailLetterRequest,
            @PathVariable int chatId
            ) throws Exception {
        messageSender.send(chatId, emailLetterRequest.messageContent());
    }
}
