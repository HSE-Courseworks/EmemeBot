package ru.mamakapa.telegramBot.data;

import java.util.List;

public record LetterToUser(
        String messageContent,
        Long chatId,
        List<String> fileKeys
) {
}
