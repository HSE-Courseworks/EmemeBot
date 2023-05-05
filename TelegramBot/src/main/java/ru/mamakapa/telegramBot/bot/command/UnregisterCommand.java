package ru.mamakapa.telegramBot.bot.command;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.mamakapa.ememeSenderFunctionality.bot.EmemeBotFunctionality;
import ru.mamakapa.ememeSenderFunctionality.bot.command.BotCommand;
import ru.mamakapa.ememeSenderFunctionality.bot.service.MessageSender;

public class UnregisterCommand extends BotCommand<Message> {
    private final EmemeBotFunctionality ememeBotFunctionality;
    private final MessageSender<Integer, String> messageSender;
    public UnregisterCommand(EmemeBotFunctionality ememeBotFunctionality, MessageSender<Integer, String> messageSender) {
        super("/unregister");
        this.ememeBotFunctionality = ememeBotFunctionality;
        this.messageSender = messageSender;
    }

    @Override
    public void execute(Message message) {
        try {
            ememeBotFunctionality.unregisterUser(Math.toIntExact(message.getChatId()));
            messageSender.send(Math.toIntExact(message.getChatId()), "You was unregistered");
        } catch (Exception ignored) {}
    }
}
