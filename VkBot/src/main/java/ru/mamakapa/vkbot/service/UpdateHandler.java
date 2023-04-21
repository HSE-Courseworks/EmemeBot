package ru.mamakapa.vkbot.service;

public interface UpdateHandler<UPDATE> {
    String handle(UPDATE update);
}
