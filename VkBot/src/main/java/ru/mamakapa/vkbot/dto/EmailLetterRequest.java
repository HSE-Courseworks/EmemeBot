package ru.mamakapa.vkbot.dto;

import java.util.List;

public record EmailLetterRequest(
        String messageContent,
        List<String> fileLinks
) {}
