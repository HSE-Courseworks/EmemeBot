package ru.mamakapa.telegramBot.bot.command;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.mamakapa.ememeSenderFunctionality.bot.EmemeBotFunctionality;
import ru.mamakapa.ememeSenderFunctionality.bot.command.BotCommand;
import ru.mamakapa.ememeSenderFunctionality.bot.data.EmailData;
import ru.mamakapa.ememeSenderFunctionality.bot.service.MessageSender;

public class AllEmailsCommand extends BotCommand<Message> {
    private final EmemeBotFunctionality ememeBotFunctionality;
    private final MessageSender<Integer, String> messageSender;
    public AllEmailsCommand(EmemeBotFunctionality ememeBotFunctionality, MessageSender<Integer, String> messageSender) {
        super("/allemails");
        this.ememeBotFunctionality = ememeBotFunctionality;
        this.messageSender = messageSender;
    }

    @Override
    public void execute(Message message) {
        try {
            messageSender.send(Math.toIntExact(message.getChatId()),
                    ememeBotFunctionality.getAllEmails(Math.toIntExact(message.getChatId()))
                            .stream().map(EmailData::address)
                            .reduce("%s\n%s"::formatted)
                            .orElse("Empty list of emails")
                    );
        } catch (Exception ignored) {}
    }
}
