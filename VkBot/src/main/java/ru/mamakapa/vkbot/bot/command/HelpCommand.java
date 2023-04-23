package ru.mamakapa.vkbot.bot.command;

import com.vk.api.sdk.objects.messages.Message;
import ru.mamakapa.ememeSenderFunctionality.bot.command.BotCommand;
import ru.mamakapa.vkbot.bot.VkBot;
import ru.mamakapa.vkbot.bot.data.VkRecipient;

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
            vkBot.send(new VkRecipient(message.getPeerId()), HELP_MESSAGE_TEXT);
        } catch (Exception ignored){}
    }
}
