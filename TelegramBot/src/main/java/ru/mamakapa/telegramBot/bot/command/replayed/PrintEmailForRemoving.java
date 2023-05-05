package ru.mamakapa.telegramBot.bot.command.replayed;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.mamakapa.ememeSenderFunctionality.bot.EmemeBotFunctionality;
import ru.mamakapa.ememeSenderFunctionality.bot.command.BotCommand;
import ru.mamakapa.ememeSenderFunctionality.bot.service.MessageSender;
import ru.mamakapa.telegramBot.bot.command.DeleteEmailCommand;

public class PrintEmailForRemoving extends BotCommand<Message> {
    private final EmemeBotFunctionality ememeBotFunctionality;
    private final MessageSender<Integer, String> messageSender;
    public PrintEmailForRemoving(EmemeBotFunctionality ememeBotFunctionality, MessageSender<Integer, String> messageSender) {
        super(DeleteEmailCommand.DELETE_EMAIL_TEXT);
        this.ememeBotFunctionality = ememeBotFunctionality;
        this.messageSender = messageSender;
    }

    @Override
    public void execute(Message message) {
        try {
            ememeBotFunctionality.deleteEmail(Math.toIntExact(message.getChatId()), message.getText());
            messageSender.send(Math.toIntExact(message.getChatId()), "Email was removed!");
        } catch (Exception ignored) {}
    }
}
