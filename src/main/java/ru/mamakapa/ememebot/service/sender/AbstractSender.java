package ru.mamakapa.ememebot.service.sender;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractSender implements Sender {
    @Autowired
    private BotConfig botConfig;
}
