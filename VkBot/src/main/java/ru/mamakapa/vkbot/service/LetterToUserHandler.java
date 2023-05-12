package ru.mamakapa.vkbot.service;

import org.springframework.stereotype.Service;
import ru.mamakapa.ememeSenderFunctionality.bot.service.MessageSender;
import ru.mamakapa.vkbot.data.LetterToUser;

@Service
public class LetterToUserHandler {
    private final MessageSender<Integer, String> messageSender;

    public LetterToUserHandler(MessageSender<Integer, String> messageSender) {
        this.messageSender = messageSender;
    }

    public void handle(LetterToUser letterToUser) throws Exception {
        messageSender.send(Math.toIntExact(letterToUser.chatId()), letterToUser.messageContent());
    }
}
