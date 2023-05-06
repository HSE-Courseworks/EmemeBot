package ru.mamakapa.ememeSenderFunctionality.bot.dto;

public record AddEmail(
        String address,
        String appPassword,
        String host
) {
}
