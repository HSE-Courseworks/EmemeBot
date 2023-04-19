package ru.mamakapa.ememeemail.services;

import ru.mamakapa.ememeemail.entities.ImapEmail;

import java.util.List;

public interface ImapEmailService {
    List<ImapEmail> getAllEmailsForChatId(Long chatId);

    ImapEmail add(Long chatId, String email, String password, String host);

    ImapEmail remove(Long chatId, String email);

    void patch(ImapEmail emailWithUpdates);

}
