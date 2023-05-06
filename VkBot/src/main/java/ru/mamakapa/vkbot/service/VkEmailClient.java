package ru.mamakapa.vkbot.service;

import org.springframework.stereotype.Component;
import ru.mamakapa.ememeSenderFunctionality.bot.EmemeBotFunctionality;
import ru.mamakapa.ememeSenderFunctionality.bot.data.EmailData;
import ru.mamakapa.ememeSenderFunctionality.bot.data.MessengerType;
import ru.mamakapa.ememeSenderFunctionality.bot.dto.AddEmail;
import ru.mamakapa.ememeSenderFunctionality.bot.dto.DeleteEmail;
import ru.mamakapa.ememeSenderFunctionality.bot.service.EmemeEmailHttpClient;

import java.util.List;

@Component
public class VkEmailClient implements EmemeBotFunctionality {
    private final EmemeEmailHttpClient ememeEmailHttpClient;

    public VkEmailClient(EmemeEmailHttpClient ememeEmailHttpClient) {
        this.ememeEmailHttpClient = ememeEmailHttpClient;
    }

    @Override
    public void registerUser(int chatId) {
        ememeEmailHttpClient.registerChat(MessengerType.VK, chatId);
    }

    @Override
    public void unregisterUser(int chatId) {
        ememeEmailHttpClient.unregisterChat(MessengerType.VK, chatId);
    }

    @Override
    public void addEmail(int chatId, EmailData emailData) {
        ememeEmailHttpClient.addEmail(
                MessengerType.VK,
                chatId,
                new AddEmail(emailData.address(), emailData.password(), emailData.host())
        );
    }

    @Override
    public List<EmailData> getAllEmails(int chatId) {
        return ememeEmailHttpClient
                .getAllEmails(MessengerType.VK, chatId)
                .emails();
    }

    @Override
    public void deleteEmail(int chatId, String address) {
        ememeEmailHttpClient.deleteEmail(MessengerType.VK, chatId, new DeleteEmail(address));
    }
}
