package ru.mamakapa.ememeemail.services;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import ru.mamakapa.ememeemail.DTOs.requests.LetterContent;

public interface TgBotClient {
    @PostExchange("/tgbot/updates/{chatId}")
    void sendUpdateToTgBot(@PathVariable Long chatId, @RequestBody LetterContent letterContent);
}
