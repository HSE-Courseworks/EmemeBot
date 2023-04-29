package ru.mamakapa.ememeemail.entities;

import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BotUser {
    private Long id;
    private Long chatId;

    public BotUser(Long chatId) {
        this.chatId = chatId;
    }
}
