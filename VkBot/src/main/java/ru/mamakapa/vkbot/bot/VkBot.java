package ru.mamakapa.vkbot.bot;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import org.springframework.stereotype.Service;
import ru.mamakapa.ememeSenderFunctionality.bot.command.CommandHandler;
import ru.mamakapa.ememeSenderFunctionality.bot.command.exception.NonHandleCommandException;
import ru.mamakapa.vkbot.bot.command.StartCommand;
import ru.mamakapa.vkbot.bot.data.VkRecipient;
import ru.mamakapa.vkbot.bot.handler.CallbackHandler;
import ru.mamakapa.vkbot.config.VkBotConfig;
import ru.mamakapa.vkbot.service.MessageSender;
import ru.mamakapa.vkbot.service.UpdateHandler;

import java.util.List;
import java.util.Random;

@Service
public class VkBot implements MessageSender<VkRecipient, String>, UpdateHandler<String> {
    private final VkApiClient vkApiClient;
    private final GroupActor groupActor;
    private final CallbackHandler callbackHandler;
    private final Random random = new Random();
    private final CommandHandler<Message> commandHandler = new CommandHandler<>(
            List.of(
                    new StartCommand()
            ), Message::getText
    );

    public VkBot(VkBotConfig config) {
        this.vkApiClient = new VkApiClient(new HttpTransportClient());
        this.groupActor = new GroupActor(config.groupId(), config.token());
        this.callbackHandler = new CallbackHandler(config.callback().confirmationCode(), config.callback().secret()) {
            @Override
            protected void messageNew(Integer groupId, Message message) {
                try{
                    commandHandler.handle(message);
                }catch (NonHandleCommandException ignored){}
            }
        };
    }
    @Override
    public String handle(String update) {
        return callbackHandler.parse(update);
    }

    @Override
    public void send(VkRecipient vkRecipient, String messageText) throws Exception{
        sendMessageText(vkRecipient.chatId(), messageText);
    }

    private void sendMessageText(int chatId, String messageText) throws ClientException, ApiException {
        this.vkApiClient.messages()
                .send(groupActor)
                .randomId(getRandomMessageId())
                .message(messageText)
                .peerId(chatId)
                .execute();
    }

    private int getRandomMessageId() {
        return random.nextInt(Integer.MAX_VALUE);
    }
}
