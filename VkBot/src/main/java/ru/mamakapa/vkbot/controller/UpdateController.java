package ru.mamakapa.vkbot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.mamakapa.vkbot.dto.EmailLetterRequest;

@RestController
@RequestMapping("vkbot/updates")
@Slf4j
public class UpdateController {
    @PostMapping("{chatId}")
    public void getUpdates(
            @RequestBody EmailLetterRequest emailLetterRequest,
            @PathVariable int chatId){
        log.info("Send %d message %s".formatted(chatId, emailLetterRequest.messageContent()));
    }
}
