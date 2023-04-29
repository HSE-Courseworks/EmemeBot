package ru.mamakapa.vkbot.bot.command;

import com.vk.api.sdk.objects.messages.Message;
import ru.mamakapa.ememeSenderFunctionality.bot.command.BotCommand;
import ru.mamakapa.vkbot.bot.VkBot;

public class DeleteEmailCommand extends BotCommand<Message> {
    public final static String DELETE_EMAIL_MESSAGE = "Print your email address for removing with replying to this message";
    private final VkBot vkBot;
    public DeleteEmailCommand(VkBot vkBot) {
        super("/deleteEmail");
        this.vkBot = vkBot;
    }

    @Override
    public void execute(Message message) {
        try {
            vkBot.sendMessageWithPayload(message.getPeerId(),
                    DELETE_EMAIL_MESSAGE,
                    """
                            {
                                "chat_id":"%d"
                            }
                            """.formatted(message.getPeerId()));
        } catch (Exception ignored) {}
    }
}
