package ru.mamakapa.vkbot.bot;

import com.google.gson.Gson;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.*;
import org.springframework.stereotype.Service;
import ru.mamakapa.ememeSenderFunctionality.bot.EmemeBotFunctionality;
import ru.mamakapa.ememeSenderFunctionality.bot.command.CommandHandler;
import ru.mamakapa.ememeSenderFunctionality.bot.command.exception.NonHandleCommandException;
import ru.mamakapa.vkbot.bot.command.*;
import ru.mamakapa.vkbot.bot.command.replayed.PrintEmailForRemoving;
import ru.mamakapa.vkbot.bot.command.replayed.PrintNewEmailAddress;
import ru.mamakapa.vkbot.bot.command.replayed.PrintNewPassword;
import ru.mamakapa.vkbot.bot.handler.CallbackHandler;
import ru.mamakapa.vkbot.config.VkBotConfig;
import ru.mamakapa.vkbot.service.MessageSender;
import ru.mamakapa.vkbot.service.UpdateHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class VkBot implements MessageSender<Integer, String>, UpdateHandler<String> {
    private static final int MAX_SIZE_KEYBOARD_BUTTONS = 10;
    private static final int MAX_COLUMN_COUNT = 2;
    private final VkApiClient vkApiClient;
    private final GroupActor groupActor;
    private final CallbackHandler callbackHandler;
    private final Random random = new Random();
    private final CommandHandler<Message> commandHandler;
    private final CommandHandler<Message> replayedCommandHandler;
    private final Keyboard commandButtonsKeyboard;

    public VkBot(VkBotConfig config, EmemeBotFunctionality ememeBotFunctionality) {
        Gson gson = new Gson();
        this.vkApiClient = new VkApiClient(new HttpTransportClient());
        this.groupActor = new GroupActor(config.groupId(), config.token());
        this.callbackHandler = new CallbackHandler(config.callback().confirmationCode(), config.callback().secret()) {
            @Override
            protected void messageNew(Integer groupId, Message message) {
                try{
                    handleReplayedMessage(message);
                    commandHandler.handle(message);
                }catch (NonHandleCommandException ignored){}
            }
        };
        this.commandHandler = new CommandHandler<>(
                List.of(
                        new StartCommand(ememeBotFunctionality, this),
                        new HelpCommand(this),
                        new AddEmailCommand(this),
                        new DeleteEmailCommand(this),
                        new AllEmailCommand(this, ememeBotFunctionality),
                        new UnregisterUserCommand(ememeBotFunctionality, this)
                ), Message::getText
        );
        this.replayedCommandHandler = new CommandHandler<>(
                List.of(
                        new PrintNewEmailAddress(this, gson),
                        new PrintNewPassword(ememeBotFunctionality, this, gson),
                        new PrintEmailForRemoving(gson, ememeBotFunctionality, this)
                ),
                message -> message.getReplyMessage().getText()
        );
        this.commandButtonsKeyboard = new Keyboard();
        List<String> commands = commandHandler.getAllCommands().limit(MAX_SIZE_KEYBOARD_BUTTONS).toList();
        List<List<KeyboardButton>> buttons = new ArrayList<>();
        for(int i = 0; i<commands.size()/MAX_COLUMN_COUNT; i++){
            List<KeyboardButton> buttonList = new ArrayList<>();
            for(int j=i*MAX_COLUMN_COUNT; j<(i+1)*MAX_COLUMN_COUNT&&j<commands.size(); j++){
                KeyboardButton keyboardButton = new KeyboardButton();
                KeyboardButtonAction action = new KeyboardButtonAction();
                action.setLabel(commands.get(j));
                action.setType(TemplateActionTypeNames.TEXT);
                keyboardButton.setAction(action);
                keyboardButton.setColor(KeyboardButtonColor.DEFAULT);
                buttonList.add(keyboardButton);
            }
            buttons.add(buttonList);
        }
        commandButtonsKeyboard.setButtons(buttons);
        commandButtonsKeyboard.setInline(true);
    }

    private void handleReplayedMessage(Message message) throws NonHandleCommandException {
        if(message.getReplyMessage()!=null){
            replayedCommandHandler.handle(message);
        }
    }

    @Override
    public String handle(String update) {
        return callbackHandler.parse(update);
    }

    @Override
    public void send(Integer chatId, String messageText) throws Exception{
        sendMessageText(chatId, messageText);
    }

    public void sendMessageWithPayload(int chatId, String message, String payload) throws ClientException, ApiException {
        vkApiClient.messages()
                .send(groupActor)
                .message(message)
                .randomId(getRandomMessageId())
                .peerId(chatId)
                .payload(payload)
                .execute();
    }

    public void sendCommandButtons(int chatId) throws ClientException, ApiException {
        vkApiClient.messages()
                .send(groupActor)
                .randomId(getRandomMessageId())
                .message("Choose another functions:")
                .keyboard(commandButtonsKeyboard)
                .peerId(chatId)
                .execute();
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
