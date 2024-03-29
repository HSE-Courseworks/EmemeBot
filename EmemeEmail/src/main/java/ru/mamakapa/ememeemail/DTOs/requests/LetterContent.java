package ru.mamakapa.ememeemail.DTOs.requests;

import lombok.Builder;

import java.util.List;

@Builder
public record LetterContent(String messageContent, Long chatId, List<String> fileKeys) {
}
