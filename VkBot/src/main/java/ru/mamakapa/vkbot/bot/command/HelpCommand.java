package ru.mamakapa.vkbot.bot.command;

import com.vk.api.sdk.objects.messages.Message;
import ru.mamakapa.ememeSenderFunctionality.bot.command.BotCommand;
import ru.mamakapa.vkbot.bot.VkBot;

public class HelpCommand extends BotCommand<Message> {
    private static final String HELP_MESSAGE_TEXT = """
            Начать - зарегистрировать пользователя
            /help - вывод всех функций бота
            /addEmail - добавить новый Email для отслеживания новых писем
            /deleteEmail - удалить отслеживание новых писем с Email
            /allEmails - вывод отслеживаемых Email почт
            /unregister - разлогинить пользователя
            """;
    private final VkBot vkBot;
    public HelpCommand(VkBot vkBot) {
        super("/help");
        this.vkBot = vkBot;
    }

    @Override
    public void execute(Message message) {
        try {
            vkBot.send(message.getPeerId(), HELP_MESSAGE_TEXT);
            vkBot.sendCommandButtons(message.getPeerId());
        } catch (Exception ignored){}
    }
}
