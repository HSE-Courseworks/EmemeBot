package ru.mamakapa.ememeSenderFunctionality.bot.dto;

import ru.mamakapa.ememeSenderFunctionality.bot.data.EmailData;

import java.util.List;

public record AllEmails(
        List<EmailData> emails
) {
}
