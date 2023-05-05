package ru.mamakapa.ememeSenderFunctionality.bot;

import ru.mamakapa.ememeSenderFunctionality.bot.data.EmailData;

import java.util.List;

public interface EmemeBotFunctionality {
    void registerUser(int chatId);
    void unregisterUser(int chatId);
    void addEmail(int chatId, EmailData emailData);
    List<EmailData> getAllEmails(int chatId);
    void deleteEmail(int chatId, String address);
}
