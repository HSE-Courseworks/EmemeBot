package ru.mamakapa.ememeemail.services;

public interface BotUserService {
    void register(Long chatId);
    void delete(Long chatId);
}
