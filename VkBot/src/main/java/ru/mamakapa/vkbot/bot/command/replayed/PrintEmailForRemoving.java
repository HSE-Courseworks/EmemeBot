package ru.mamakapa.vkbot.bot.command.replayed;

import com.google.gson.Gson;
import com.vk.api.sdk.objects.messages.Message;
import ru.mamakapa.ememeSenderFunctionality.bot.EmemeBotFunctionality;
import ru.mamakapa.ememeSenderFunctionality.bot.command.BotCommand;
import ru.mamakapa.vkbot.bot.VkBot;
import ru.mamakapa.vkbot.bot.command.DeleteEmailCommand;
import ru.mamakapa.vkbot.bot.data.VkRecipient;

import java.util.Map;

public class PrintEmailForRemoving extends BotCommand<Message> {
    private final Gson gson;
    private final EmemeBotFunctionality ememeBotFunctionality;
    private final VkBot vkBot;
    public PrintEmailForRemoving(Gson gson, EmemeBotFunctionality ememeBotFunctionality, VkBot vkBot) {
        super(DeleteEmailCommand.DELETE_EMAIL_MESSAGE);
        this.gson = gson;
        this.ememeBotFunctionality = ememeBotFunctionality;
        this.vkBot = vkBot;
    }

    @Override
    public void execute(Message message) {
        try {
            Map<String, String> userData = gson.fromJson(message.getReplyMessage().getPayload(), Map.class);
            int chatId = Integer.parseInt(userData.get("chat_id"));
            String emailAddress = message.getText();
            ememeBotFunctionality.deleteEmail(chatId, emailAddress);
            vkBot.send(new VkRecipient(chatId), """
                    Email with address %s was successfully removed!
                    """.formatted(emailAddress));
        } catch (Exception ignored) {}
    }
}
