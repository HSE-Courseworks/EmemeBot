package ru.mamakapa.telegramBot.bot.command;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.mamakapa.ememeSenderFunctionality.bot.command.BotCommand;
import ru.mamakapa.telegramBot.bot.TgBot;

public class DeleteEmailCommand extends BotCommand<Message> {
    public final static String DELETE_EMAIL_TEXT = "Print email address for removing";
    private final TgBot tgBot;
    public DeleteEmailCommand(TgBot tgBot) {
        super("/deleteemail");
        this.tgBot = tgBot;
    }

    @Override
    public void execute(Message message) {
        tgBot.sendMessageWithReply(Math.toIntExact(message.getChatId()), DELETE_EMAIL_TEXT, "yourEmailAdress@host.com");
    }
}
