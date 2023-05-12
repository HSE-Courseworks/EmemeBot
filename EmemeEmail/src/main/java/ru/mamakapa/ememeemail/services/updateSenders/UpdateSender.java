package ru.mamakapa.ememeemail.services.updateSenders;

import ru.mamakapa.ememeemail.DTOs.requests.LetterContent;
import ru.mamakapa.ememeemail.entities.BotUser;

public interface UpdateSender {
    void sendUpdate(BotUser user, LetterContent content);
}
