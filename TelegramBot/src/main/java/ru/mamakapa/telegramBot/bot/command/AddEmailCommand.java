package ru.mamakapa.telegramBot.bot.command;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.mamakapa.ememeSenderFunctionality.bot.command.BotCommand;
import ru.mamakapa.telegramBot.bot.TgBot;

public class AddEmailCommand extends BotCommand<Message> {
    public static final String NEW_EMAIL_TEXT = "Enter your email address and password through space with replying to this message";
    private final TgBot tgBot;
    public AddEmailCommand(TgBot tgBot) {
        super("/addemail");
        this.tgBot = tgBot;
    }

    @Override
    public void execute(Message message) {
        tgBot.sendMessageWithReply(Math.toIntExact(message.getChatId()), NEW_EMAIL_TEXT, "myemail@host.com ************");
    }
}
