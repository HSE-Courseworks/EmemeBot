package ru.mamakapa.vkbot.bot.command.replayed;

import com.google.gson.Gson;
import com.vk.api.sdk.objects.messages.Message;
import ru.mamakapa.ememeSenderFunctionality.bot.EmemeBotFunctionality;
import ru.mamakapa.ememeSenderFunctionality.bot.command.BotCommand;
import ru.mamakapa.ememeSenderFunctionality.bot.data.EmailData;
import ru.mamakapa.vkbot.bot.VkBot;

import java.util.Map;

public class PrintNewHost extends BotCommand<Message> {
    private final VkBot vkBot;
    private final EmemeBotFunctionality ememeBotFunctionality;
    private final Gson gson;

    public PrintNewHost(VkBot vkBot, EmemeBotFunctionality ememeBotFunctionality, Gson gson) {
        super(PrintNewPassword.PRINT_NEW_EMAIL_HOST_TEXT);
        this.vkBot = vkBot;
        this.ememeBotFunctionality = ememeBotFunctionality;
        this.gson = gson;
    }

    @Override
    public void execute(Message message) {
        try {
            Map<String, String> emailDataMap = gson.fromJson(message.getReplyMessage().getPayload(), Map.class);
            int chatId = Integer.parseInt(emailDataMap.get("chat_id"));
            EmailData emailData = new EmailData(
                    emailDataMap.get("email_address"),
                    emailDataMap.get("email_password"),
                    message.getText()
            );
            ememeBotFunctionality.addEmail(chatId, emailData);
            vkBot.send(chatId,
                    "Your email with data: %s was successfully added!"
                            .formatted(emailData.toString()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
