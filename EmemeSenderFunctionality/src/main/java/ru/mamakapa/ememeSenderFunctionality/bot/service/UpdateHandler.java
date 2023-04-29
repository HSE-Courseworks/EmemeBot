package ru.mamakapa.ememeSenderFunctionality.bot.service;

public interface UpdateHandler<UPDATE> {
    String handle(UPDATE update);
}
