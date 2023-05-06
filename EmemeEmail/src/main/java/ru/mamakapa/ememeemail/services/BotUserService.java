package ru.mamakapa.ememeemail.services;

import ru.mamakapa.ememeemail.DTOs.requests.MessengerType;

public interface BotUserService {
    void register(Long chatId, MessengerType messengerType);
    void delete(Long chatId, MessengerType messengerType);
}
