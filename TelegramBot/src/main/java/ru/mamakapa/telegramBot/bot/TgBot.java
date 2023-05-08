package ru.mamakapa.telegramBot.bot;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mamakapa.ememeSenderFunctionality.bot.EmemeBotFunctionality;
import ru.mamakapa.ememeSenderFunctionality.bot.command.CommandHandler;
import ru.mamakapa.ememeSenderFunctionality.bot.command.exception.NonHandleCommandException;
import ru.mamakapa.ememeSenderFunctionality.bot.service.MessageSender;
import ru.mamakapa.ememeSenderFunctionality.bot.service.UpdateHandler;
import ru.mamakapa.telegramBot.bot.command.*;
import ru.mamakapa.telegramBot.bot.command.replayed.PrintEmailForRemoving;
import ru.mamakapa.telegramBot.bot.command.replayed.PrintNewEmail;
import ru.mamakapa.telegramBot.configuration.TelegramConfiguration;

import java.util.List;

@Component
public class TgBot extends DefaultAbsSender implements UpdateHandler<Update>, MessageSender<Integer, String> {
    private final CommandHandler<Message> commandHandler;
    private final CommandHandler<Message> replayedCommandHandler;

    public TgBot(
            TelegramConfiguration tgConfig,
            @Qualifier("telegramEmailClient") EmemeBotFunctionality ememeBotFunctionality
    ) {
        super(new DefaultBotOptions(), tgConfig.token());
        this.commandHandler = new CommandHandler<>(
                List.of(
                        new StartCommand(ememeBotFunctionality, this),
                        new HelpCommand(this),
                        new AddEmailCommand(this),
                        new DeleteEmailCommand(this),
                        new AllEmailsCommand(ememeBotFunctionality, this),
                        new UnregisterCommand(ememeBotFunctionality, this)
                ),
                Message::getText
        );
        this.replayedCommandHandler = new CommandHandler<>(
                List.of(
                        new PrintNewEmail(this, ememeBotFunctionality),
                        new PrintEmailForRemoving(ememeBotFunctionality, this)
                ),
                message -> message.getReplyToMessage().getText()
        );
    }

    @Override
    public String handle(Update update) {
        try {
            handleReplayedMessage(update);
            handleMessage(update);
        } catch (TelegramApiException | NonHandleCommandException ignored) {
        }
        return "OK";
    }

    private void handleReplayedMessage(Update update) throws NonHandleCommandException {
        if (update.hasMessage() && update.getMessage().getReplyToMessage() != null) {
            replayedCommandHandler.handle(update.getMessage());
        }
    }

    private void handleMessage(Update update) throws TelegramApiException, NonHandleCommandException {
        if (update.hasMessage() && update.getMessage().hasText()) {
            commandHandler.handle(update.getMessage());
        }
    }
    @Override
    public void send(Integer chatId, String message) throws Exception {
        execute(new SendMessage(String.valueOf(chatId), message));
    }

    public void sendMessageWithReply(int chatId, String message, String placeholder) {
        try {
            execute(SendMessage.builder()
                    .chatId((long) chatId)
                    .text(message)
                    .replyMarkup(new ForceReplyKeyboard(true, true, placeholder))
                    .build());
        } catch (TelegramApiException ignored) {
        }
    }
}
