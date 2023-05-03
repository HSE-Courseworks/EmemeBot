package ru.mamakapa.telegramBot.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mamakapa.ememeSenderFunctionality.bot.service.MessageSender;
import ru.mamakapa.ememeSenderFunctionality.bot.service.UpdateHandler;
import ru.mamakapa.telegramBot.configuration.TelegramConfiguration;

@Component
@Slf4j
public class TgBot extends DefaultAbsSender implements UpdateHandler<Update>, MessageSender<Integer, String> {
    public TgBot(TelegramConfiguration tgConfig) {
        super(new DefaultBotOptions(), tgConfig.token());
    }

    @Override
    public String handle(Update update) {
        try {
            log.info("new update = {}", update);
            if(update.hasMessage()) {
                Message message = update.getMessage();
                execute(new SendMessage(String.valueOf(message.getChatId()), "Hello, %s".formatted(message.getFrom().getUserName())));
            }
        } catch (TelegramApiException ignored) {}
        return "OK";
    }

    @Override
    public void send(Integer chatId, String message) throws Exception {
        execute(new SendMessage(String.valueOf(chatId), message));
    }
}
