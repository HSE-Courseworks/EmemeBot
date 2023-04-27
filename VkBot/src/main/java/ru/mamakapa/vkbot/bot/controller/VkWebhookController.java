package ru.mamakapa.vkbot.bot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mamakapa.ememeSenderFunctionality.bot.service.UpdateHandler;

@RestController
@RequestMapping("/")
public class VkWebhookController {
    private final UpdateHandler<String> updateHandler;

    public VkWebhookController(UpdateHandler<String> updateHandler) {
        this.updateHandler = updateHandler;
    }

    @PostMapping
    public String handleUpdate(@RequestBody String json){
        return updateHandler.handle(json);
    }
}
