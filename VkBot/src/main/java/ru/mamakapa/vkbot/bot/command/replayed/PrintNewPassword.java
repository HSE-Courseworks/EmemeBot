package ru.mamakapa.vkbot.bot.command.replayed;

import com.google.gson.Gson;
import com.vk.api.sdk.objects.messages.Message;
import ru.mamakapa.ememeSenderFunctionality.bot.EmemeBotFunctionality;
import ru.mamakapa.ememeSenderFunctionality.bot.command.BotCommand;
import ru.mamakapa.vkbot.bot.VkBot;

import java.util.Map;

public class PrintNewPassword extends BotCommand<Message> {
    private final Gson gson;
    private final EmemeBotFunctionality ememeBotFunctionality;
    private final VkBot vkBot;
    public final static String PRINT_NEW_EMAIL_HOST_TEXT = "Print your email host with replied to this message";

    public PrintNewPassword(EmemeBotFunctionality ememeBotFunctionality, VkBot vkBot, Gson gson) {
        super(PrintNewEmailAddress.SEND_NEW_PASSWORD);
        this.vkBot = vkBot;
        this.ememeBotFunctionality = ememeBotFunctionality;
        this.gson = gson;
    }

    @Override
    public void execute(Message message) {
        try {
            Map<String, String> userData = gson.fromJson(message.getReplyMessage().getPayload(), Map.class);
            vkBot.sendMessageWithPayload(message.getPeerId(), PRINT_NEW_EMAIL_HOST_TEXT, """
                    {
                        "email_address":"%s",
                        "chat_id":"%s",
                        "email_password":"%s"
                    }
                    """.formatted(
                    userData.get("email_address"),
                    userData.get("chat_id"),
                    message.getText())
            );
        } catch (Exception ignored) {
        }
    }
}
