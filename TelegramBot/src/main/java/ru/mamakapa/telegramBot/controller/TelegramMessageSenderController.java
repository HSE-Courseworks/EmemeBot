package ru.mamakapa.telegramBot.controller;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;
import ru.mamakapa.ememeSenderFunctionality.bot.dto.EmailLetterRequest;
import ru.mamakapa.telegramBot.data.LetterToUser;
import ru.mamakapa.telegramBot.service.LetterToUserHandler;

@RestController
@ConditionalOnProperty(prefix = "kafka", name = "isEnabled", havingValue = "false")
@RequestMapping("tgbot/updates")
public class TelegramMessageSenderController {
    private final LetterToUserHandler letterToUserHandler;

    public TelegramMessageSenderController(LetterToUserHandler letterToUserHandler) {
        this.letterToUserHandler = letterToUserHandler;
    }

    @PostMapping("{chatId}")
    public void getUpdates(
            @RequestBody EmailLetterRequest emailLetterRequest,
            @PathVariable int chatId
    ) throws Exception {
        letterToUserHandler.handle(
                new LetterToUser(
                        (long) chatId,
                        emailLetterRequest.messageContent(),
                        emailLetterRequest.fileLinks()
                ));
    }
}
