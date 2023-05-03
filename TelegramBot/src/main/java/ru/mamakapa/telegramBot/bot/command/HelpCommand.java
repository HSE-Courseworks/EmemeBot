package ru.mamakapa.telegramBot.bot.command;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.mamakapa.ememeSenderFunctionality.bot.command.BotCommand;
import ru.mamakapa.ememeSenderFunctionality.bot.service.MessageSender;

public class HelpCommand extends BotCommand<Message> {
    private final static String HELP_TEXT = """
            /start - зарегистрировать пользователя
            /help - вывод всех функций бота
            /addemail - добавить новый Email для отслеживания новых писем
            /deleteemail - удалить отслеживание новых писем с Email
            /allemails - вывод отслеживаемых Email почт
            /unregister - разлогинить пользователя
            """;
    private final MessageSender<Integer, String> messageSender;
    public HelpCommand(MessageSender<Integer, String> messageSender) {
        super("/help");
        this.messageSender = messageSender;
    }

    @Override
    public void execute(Message message) {
        try {
            messageSender.send(Math.toIntExact(message.getChatId()), HELP_TEXT);
        } catch (Exception ignored) {}
    }
}
