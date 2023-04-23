package ru.mamakapa.ememeSenderFunctionality.bot;

import ru.mamakapa.ememeSenderFunctionality.bot.data.EmailData;

public interface EmemeBotFunctionality {
    void registerUser();
    void unregisterUser();
    void addEmail(EmailData emailData);
    void sendAllEmails();
    void deleteEmail(String address);
}
