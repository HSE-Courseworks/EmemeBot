package ru.mamakapa.vkbot.bot.command;

import com.vk.api.sdk.objects.messages.Message;
import ru.mamakapa.ememeSenderFunctionality.bot.command.BotCommand;
import ru.mamakapa.vkbot.bot.VkBot;

public class AddEmailCommand extends BotCommand<Message> {
    public static final String ADD_NEW_EMAIL_MESSAGE = "Print your email address with replying to this message";
    private final VkBot vkBot;
    public AddEmailCommand(VkBot vkBot) {
        super("/addEmail");
        this.vkBot = vkBot;
    }

    @Override
    public void execute(Message message) {
        try {
            vkBot.sendMessageWithPayload(message.getPeerId(),
                    ADD_NEW_EMAIL_MESSAGE,
                    """
                            {
                                "chat_id":"%d"
                            }
                            """.formatted(message.getPeerId()));
        } catch (Exception ignored) {}
    }
}
