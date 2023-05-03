package ru.mamakapa.vkbot.bot.command.replayed;

import com.google.gson.Gson;
import com.vk.api.sdk.objects.messages.Message;
import ru.mamakapa.ememeSenderFunctionality.bot.EmemeBotFunctionality;
import ru.mamakapa.ememeSenderFunctionality.bot.command.BotCommand;
import ru.mamakapa.ememeSenderFunctionality.bot.data.EmailData;
import ru.mamakapa.vkbot.bot.VkBot;

import java.util.Map;

public class PrintNewPassword extends BotCommand<Message> {
    private final Gson gson;
    private final EmemeBotFunctionality ememeBotFunctionality;
    private final VkBot vkBot;
    public PrintNewPassword(EmemeBotFunctionality ememeBotFunctionality, VkBot vkBot, Gson gson) {
        super(PrintNewEmailAddress.SEND_NEW_PASSWORD);
        this.vkBot = vkBot;
        this.ememeBotFunctionality = ememeBotFunctionality;
        this.gson = gson;
    }

    @Override
    public void execute(Message message) {
        try {
            Map<String, String> emailDataMap = gson.fromJson(message.getReplyMessage().getPayload(), Map.class);
            int chatId = Integer.parseInt(emailDataMap.get("chat_id"));
            EmailData emailData = new EmailData(emailDataMap.get("email_address"), message.getText());
            vkBot.send(chatId,
                    "Your email with data: %s was successfully added!".formatted(emailData.toString()));
            ememeBotFunctionality.addEmail(chatId, emailData);
        } catch (Exception ignored) {}
    }
}
