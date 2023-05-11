package ru.mamakapa.telegramBot.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record LetterToUser(
        Long chatId,
        String message,
        List<String> fileLinks
) {
}
