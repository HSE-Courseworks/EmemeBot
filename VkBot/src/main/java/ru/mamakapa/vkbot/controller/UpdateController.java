package ru.mamakapa.vkbot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.mamakapa.ememeSenderFunctionality.bot.dto.EmailLetterRequest;
import ru.mamakapa.ememeSenderFunctionality.bot.service.MessageSender;

@RestController
@RequestMapping("vkbot/updates")
@Slf4j
public class UpdateController {
    private final MessageSender<Integer, String> messageSender;

    public UpdateController(MessageSender<Integer, String> messageSender) {
        this.messageSender = messageSender;
    }

    @PostMapping("{chatId}")
    public void getUpdates(
            @RequestBody EmailLetterRequest emailLetterRequest,
            @PathVariable int chatId) throws Exception {
        log.info("Send %d message %s".formatted(chatId, emailLetterRequest.messageContent()));
        messageSender.send(chatId, emailLetterRequest.messageContent());
    }
}
