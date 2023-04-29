package ru.mamakapa.vkbot.bot.command;

import com.vk.api.sdk.objects.messages.Message;
import ru.mamakapa.ememeSenderFunctionality.bot.EmemeBotFunctionality;
import ru.mamakapa.ememeSenderFunctionality.bot.command.BotCommand;
import ru.mamakapa.ememeSenderFunctionality.bot.data.EmailData;
import ru.mamakapa.vkbot.bot.VkBot;

public class AllEmailCommand extends BotCommand<Message> {
    private final VkBot vkBot;
    private final EmemeBotFunctionality ememeBotFunctionality;
    public AllEmailCommand(VkBot vkBot, EmemeBotFunctionality ememeBotFunctionality) {
        super("/allEmails");
        this.vkBot = vkBot;
        this.ememeBotFunctionality = ememeBotFunctionality;
    }

    @Override
    public void execute(Message message) {
        try {
            vkBot.send(message.getPeerId(),
                    ememeBotFunctionality
                            .getAllEmails(message.getPeerId())
                            .stream()
                            .map(EmailData::address)
                            .reduce((s, s2) -> String.format("%s\n%s", s, s2))
                            .orElse("Empty data list")
            );
        } catch (Exception ignored) {}
    }
}
