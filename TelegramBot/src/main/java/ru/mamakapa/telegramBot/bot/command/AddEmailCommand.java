package ru.mamakapa.telegramBot.bot.command;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.mamakapa.ememeSenderFunctionality.bot.command.BotCommand;
import ru.mamakapa.telegramBot.bot.TgBot;

public class AddEmailCommand extends BotCommand<Message> {
    public static final String NEW_EMAIL_TEXT =
            "Enter your email Address, Password and Host through Space with Replying to this message";
    private static final String PLACEHOLDER_NEW_EMAIL_MESSAGE = "myemail@host.com ************ imap.host.ru";
    private final TgBot tgBot;

    public AddEmailCommand(TgBot tgBot) {
        super("/addemail");
        this.tgBot = tgBot;
    }

    @Override
    public void execute(Message message) {
        tgBot.sendMessageWithReply(
                Math.toIntExact(message.getChatId()),
                NEW_EMAIL_TEXT,
                PLACEHOLDER_NEW_EMAIL_MESSAGE
        );
    }
}
