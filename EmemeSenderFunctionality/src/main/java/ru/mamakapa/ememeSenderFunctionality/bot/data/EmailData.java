package ru.mamakapa.ememeSenderFunctionality.bot.data;

import jakarta.validation.constraints.Email;

public record EmailData(
        @Email
        String address,
        String password
) {}
