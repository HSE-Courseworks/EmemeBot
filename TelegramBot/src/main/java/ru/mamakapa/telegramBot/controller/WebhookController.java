package ru.mamakapa.telegramBot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.mamakapa.ememeSenderFunctionality.bot.service.UpdateHandler;

@RestController
@RequestMapping("/")
public class WebhookController {
    private final UpdateHandler<Update> updateHandler;
    public WebhookController(UpdateHandler<Update> updateHandler) {
        this.updateHandler = updateHandler;
    }

    @PostMapping("")
    public String handle(@RequestBody Update update){
        return updateHandler.handle(update);
    }
}
