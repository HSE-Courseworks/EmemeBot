package ru.mamakapa.vkbot.bot.command.replayed;

import com.google.gson.Gson;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import ru.mamakapa.ememeSenderFunctionality.bot.command.BotCommand;
import ru.mamakapa.vkbot.bot.VkBot;
import ru.mamakapa.vkbot.bot.command.AddEmailCommand;

import java.util.Map;

public class PrintNewEmailAddress extends BotCommand<Message> {
    public final static String SEND_NEW_PASSWORD = "Send password with replying to this message!";
    private final VkBot vkBot;
    private final Gson gson = new Gson();

    public PrintNewEmailAddress(VkBot vkBot) {
        super(AddEmailCommand.ADD_NEW_EMAIL_MESSAGE);
        this.vkBot = vkBot;
    }

    @Override
    public void execute(Message message) {
        try {
            Map<String, String> userData = gson.fromJson(message.getReplyMessage().getPayload(), Map.class);
            vkBot.sendMessageWithPayload(message.getPeerId(), SEND_NEW_PASSWORD, String.format(
                    """
                    {
                        "email_address":"%s",
                        "chat_id":"%s"
                    }
                    """.formatted(message.getText(), userData.get("chat_id"))
            ));
        } catch (ClientException | ApiException ignored) {}
    }
}
