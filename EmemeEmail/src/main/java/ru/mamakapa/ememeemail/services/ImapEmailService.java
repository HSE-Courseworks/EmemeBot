package ru.mamakapa.ememeemail.services;

import ru.mamakapa.ememeemail.DTOs.requests.MessengerType;
import ru.mamakapa.ememeemail.entities.ImapEmail;

import java.util.List;

public interface ImapEmailService {
    List<ImapEmail> getAllEmailsForChatId(Long chatId, MessengerType messengerType);

    ImapEmail add(Long chatId, MessengerType messengerType, String email, String password, String host);

    ImapEmail remove(Long chatId, MessengerType messengerType, String email);

    void patch(ImapEmail emailWithUpdates);

}
