package ru.mamakapa.telegramBot.bot.functionality;

import org.springframework.stereotype.Service;
import ru.mamakapa.ememeSenderFunctionality.bot.EmemeBotFunctionality;
import ru.mamakapa.ememeSenderFunctionality.bot.data.EmailData;
import ru.mamakapa.ememeSenderFunctionality.bot.dto.AddEmail;
import ru.mamakapa.ememeSenderFunctionality.bot.dto.DeleteEmail;
import ru.mamakapa.ememeSenderFunctionality.bot.service.EmemeEmailHttpClient;

import java.util.List;

import static ru.mamakapa.ememeSenderFunctionality.bot.data.MessengerType.TG;

@Service
public class TelegramEmailClient implements EmemeBotFunctionality {
    private final EmemeEmailHttpClient ememeEmailHttpClient;

    public TelegramEmailClient(EmemeEmailHttpClient ememeEmailHttpClient) {
        this.ememeEmailHttpClient = ememeEmailHttpClient;
    }

    @Override
    public void registerUser(int chatId) {
        ememeEmailHttpClient.registerChat(TG, chatId);
    }

    @Override
    public void unregisterUser(int chatId) {
        ememeEmailHttpClient.unregisterChat(TG, chatId);
    }

    @Override
    public void addEmail(int chatId, EmailData emailData) {
        ememeEmailHttpClient.addEmail(
                TG, chatId,
                new AddEmail(emailData.address(), emailData.password(), emailData.host())
        );
    }

    @Override
    public List<EmailData> getAllEmails(int chatId) {
        return ememeEmailHttpClient
                .getAllEmails(TG, chatId)
                .emails();
    }

    @Override
    public void deleteEmail(int chatId, String address) {
        ememeEmailHttpClient.deleteEmail(TG, chatId, new DeleteEmail(address));
    }
}
