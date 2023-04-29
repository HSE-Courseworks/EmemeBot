package ru.mamakapa.ememeemail.entities;

import lombok.*;
import ru.mamakapa.ememeemail.DTOs.requests.MessengerType;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BotUser {
    private Long id;
    private Long chatId;

    private MessengerType messengerType;

    public BotUser(Long chatId, MessengerType messengerType) {
        this.chatId = chatId;
    }
}
