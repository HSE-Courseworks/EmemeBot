package ru.mamakapa.vkbot.bot.command;

import com.vk.api.sdk.objects.messages.Message;
import ru.mamakapa.ememeSenderFunctionality.bot.EmemeBotFunctionality;
import ru.mamakapa.ememeSenderFunctionality.bot.command.BotCommand;
import ru.mamakapa.vkbot.bot.VkBot;
import ru.mamakapa.vkbot.bot.data.VkRecipient;

public class UnregisterUserCommand extends BotCommand<Message> {
    private final EmemeBotFunctionality ememeBotFunctionality;
    private final VkBot vkBot;
    public UnregisterUserCommand(EmemeBotFunctionality ememeBotFunctionality, VkBot vkBot) {
        super("/unregister");
        this.ememeBotFunctionality = ememeBotFunctionality;
        this.vkBot = vkBot;
    }

    @Override
    public void execute(Message message) {
        try {
            ememeBotFunctionality.unregisterUser(message.getPeerId());
            vkBot.send(new VkRecipient(message.getPeerId()), "You was been unregistered!");
        } catch (Exception ignored) {}
    }
}
