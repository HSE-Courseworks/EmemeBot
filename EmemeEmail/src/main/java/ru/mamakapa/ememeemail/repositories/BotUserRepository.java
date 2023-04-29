package ru.mamakapa.ememeemail.repositories;

import ru.mamakapa.ememeemail.entities.BotUser;

import java.util.List;

public interface BotUserRepository {
    BotUser add(BotUser user);
    BotUser deleteById(Long id);
    BotUser getUserByChatId(Long chatId);
    List<BotUser> getAll();
    BotUser deleteByChatId(Long chatId);
}
