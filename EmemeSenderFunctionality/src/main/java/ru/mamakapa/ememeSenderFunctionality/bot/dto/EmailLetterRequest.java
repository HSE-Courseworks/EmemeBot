package ru.mamakapa.ememeSenderFunctionality.bot.dto;

import java.util.List;

public record EmailLetterRequest(
        String messageContent,
        List<String> fileLinks
) {}
