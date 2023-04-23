package ru.mamakapa.vkbot.bot.command;

import com.vk.api.sdk.objects.messages.Message;
import ru.mamakapa.ememeSenderFunctionality.bot.command.BotCommand;

public class StartCommand extends BotCommand<Message> {
    public StartCommand() {
        super("Начать");
    }

    @Override
    public void execute(Message message) {
        
    }
}
