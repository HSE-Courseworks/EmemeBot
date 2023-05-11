package ru.mamakapa.telegramBot.data;

import java.util.List;

public record LetterToUser(
        Long chatId,
        String messageContent,
        List<String> fileLinks
) {
}
