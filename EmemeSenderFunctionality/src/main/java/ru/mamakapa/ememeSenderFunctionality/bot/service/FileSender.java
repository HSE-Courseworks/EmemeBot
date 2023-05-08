package ru.mamakapa.ememeSenderFunctionality.bot.service;

import java.io.File;

public interface FileSender {
    void send(long chatId, File file);
}
