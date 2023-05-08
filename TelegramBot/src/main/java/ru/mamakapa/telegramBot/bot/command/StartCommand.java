package ru.mamakapa.telegramBot.bot.command;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.mamakapa.ememeSenderFunctionality.bot.EmemeBotFunctionality;
import ru.mamakapa.ememeSenderFunctionality.bot.command.BotCommand;
import ru.mamakapa.ememeSenderFunctionality.bot.service.MessageSender;

public class StartCommand extends BotCommand<Message> {
    private final EmemeBotFunctionality ememeBotFunctionality;
    private final MessageSender<Integer, String> messageSender;
    public StartCommand(EmemeBotFunctionality ememeBotFunctionality, MessageSender<Integer, String> messageSender) {
        super("/start");
        this.ememeBotFunctionality = ememeBotFunctionality;
        this.messageSender = messageSender;
    }

    @Override
    public void execute(Message message) {
        try {
            ememeBotFunctionality.registerUser(Math.toIntExact(message.getChatId()));
            messageSender.send(Math.toIntExact(message.getChatId()), "You was registered!");
        } catch (Exception ignored) {}
    }
}
