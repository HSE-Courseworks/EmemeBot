package ru.mamakapa.vkbot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;
import ru.mamakapa.ememeSenderFunctionality.bot.dto.EmailLetterRequest;
import ru.mamakapa.vkbot.data.LetterToUser;
import ru.mamakapa.vkbot.service.LetterToUserHandler;

@RestController
@RequestMapping("vkbot/updates")
@Slf4j
@ConditionalOnProperty(prefix = "http.controller", name = "isEnabled", havingValue = "true")
public class UpdateController {
    private final LetterToUserHandler letterToUserHandler;

    public UpdateController(LetterToUserHandler letterToUserHandler) {
        this.letterToUserHandler = letterToUserHandler;
    }

    @PostMapping("{chatId}")
    public void getUpdates(
            @RequestBody EmailLetterRequest emailLetterRequest,
            @PathVariable int chatId) throws Exception {
        log.info("Send %d message %s".formatted(chatId, emailLetterRequest.messageContent()));
        letterToUserHandler.handle(
                new LetterToUser(
                        emailLetterRequest.messageContent(),
                        (long) chatId,
                        emailLetterRequest.fileLinks()
                ));
    }
}
