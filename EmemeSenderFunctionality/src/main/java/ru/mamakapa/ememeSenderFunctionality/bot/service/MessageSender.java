package ru.mamakapa.ememeSenderFunctionality.bot.service;

public interface MessageSender<RECIPIENT, MESSAGE> {
    void send(RECIPIENT recipient, MESSAGE message) throws Exception;
}
