package ru.mamakapa.ememeemail.services;

import ru.mamakapa.ememeemail.entities.ImapEmail;

import java.util.List;

public interface ImapEmailService {
    List<ImapEmail> getAllEmailsByBotId(Long userId);

    ImapEmail add(Long chatId, String email, String password, String host);

    ImapEmail remove(Long chatId, String email);

    ImapEmail patch(ImapEmail emailWithUpdates);

}
