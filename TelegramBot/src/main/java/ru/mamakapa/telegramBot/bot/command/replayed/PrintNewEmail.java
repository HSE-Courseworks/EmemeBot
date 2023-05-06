package ru.mamakapa.telegramBot.bot.command.replayed;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.mamakapa.ememeSenderFunctionality.bot.EmemeBotFunctionality;
import ru.mamakapa.ememeSenderFunctionality.bot.command.BotCommand;
import ru.mamakapa.ememeSenderFunctionality.bot.data.EmailData;
import ru.mamakapa.ememeSenderFunctionality.bot.service.MessageSender;
import ru.mamakapa.telegramBot.bot.command.AddEmailCommand;

public class PrintNewEmail extends BotCommand<Message> {
    private final MessageSender<Integer, String> messageSender;
    private final EmemeBotFunctionality ememeBotFunctionality;
    public PrintNewEmail(MessageSender<Integer, String> messageSender, EmemeBotFunctionality ememeBotFunctionality) {
        super(AddEmailCommand.NEW_EMAIL_TEXT);
        this.messageSender = messageSender;
        this.ememeBotFunctionality = ememeBotFunctionality;
    }

    @Override
    public void execute(Message message) {
        try{
            String[] args = message.getText().trim().replaceAll("\\s+", " ").split(" ");
            EmailData emailData = new EmailData(args[0], args[1], args[2]);
            ememeBotFunctionality.addEmail(Math.toIntExact(message.getChatId()), emailData);
            messageSender.send(Math.toIntExact(message.getChatId()), "Email was saved!");
        }catch (Exception ignored){}
    }
}
