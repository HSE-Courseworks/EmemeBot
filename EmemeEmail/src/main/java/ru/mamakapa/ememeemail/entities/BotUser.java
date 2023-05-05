package ru.mamakapa.ememeemail.entities;

import lombok.*;
<<<<<<< HEAD
import ru.mamakapa.ememeemail.DTOs.requests.MessengerType;
=======

>>>>>>> telegramBot

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BotUser {
    private Long id;
    private Long chatId;

<<<<<<< HEAD
    private MessengerType messengerType;

    public BotUser(Long chatId, MessengerType messengerType) {
=======
    public BotUser(Long chatId) {
>>>>>>> telegramBot
        this.chatId = chatId;
    }
}
