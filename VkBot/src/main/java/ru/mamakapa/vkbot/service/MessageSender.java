package ru.mamakapa.vkbot.service;

public interface MessageSender<RECIPIENT, MESSAGE> {
    void send(RECIPIENT recipient, MESSAGE message) throws Exception;
}
