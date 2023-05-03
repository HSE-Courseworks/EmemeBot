package ru.mamakapa.vkbot.bot.command;

import com.vk.api.sdk.objects.messages.Message;
import ru.mamakapa.ememeSenderFunctionality.bot.EmemeBotFunctionality;
import ru.mamakapa.ememeSenderFunctionality.bot.command.BotCommand;
import ru.mamakapa.vkbot.bot.VkBot;

public final class StartCommand extends BotCommand<Message> {
    private final EmemeBotFunctionality ememeBotFunctionality;
    private final VkBot vkBot;

    public StartCommand(EmemeBotFunctionality ememeBotFunctionality, VkBot vkBot) {
        super("Начать");
        this.ememeBotFunctionality = ememeBotFunctionality;
        this.vkBot = vkBot;
    }

    @Override
    public void execute(Message message) {
        ememeBotFunctionality.registerUser(message.getPeerId());
        try {
            vkBot.send(message.getPeerId(),
                    "Hello! You was registered!");
            vkBot.sendCommandButtons(message.getPeerId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
