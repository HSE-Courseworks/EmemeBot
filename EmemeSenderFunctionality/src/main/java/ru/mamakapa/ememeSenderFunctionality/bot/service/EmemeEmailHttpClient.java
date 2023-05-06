package ru.mamakapa.ememeSenderFunctionality.bot.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import ru.mamakapa.ememeSenderFunctionality.bot.data.EmailData;
import ru.mamakapa.ememeSenderFunctionality.bot.data.MessengerType;
import ru.mamakapa.ememeSenderFunctionality.bot.dto.AddEmail;
import ru.mamakapa.ememeSenderFunctionality.bot.dto.AllEmails;
import ru.mamakapa.ememeSenderFunctionality.bot.dto.DeleteEmail;

@HttpExchange("http://localhost:8080/")
public interface EmemeEmailHttpClient {
    @PostExchange("registerChat/{messengerType}/{chatId}")
    void registerChat(
            @PathVariable MessengerType messengerType,
            @PathVariable long chatId
    );

    @DeleteExchange("registerChat/{messengerType}/{chatId}")
    void unregisterChat(
            @PathVariable MessengerType messengerType,
            @PathVariable long chatId
    );

    @GetExchange("emails")
    AllEmails getAllEmails(
            @RequestParam MessengerType messengerType,
            @RequestParam long chatId);

    @PostExchange("emails")
    EmailData addEmail(
            @RequestParam MessengerType messengerType,
            @RequestParam long chatId,
            @RequestBody AddEmail addEmail
    );

    @DeleteExchange("emails")
    EmailData deleteEmail(
            @RequestParam MessengerType messengerType,
            @RequestParam long chatId,
            @RequestBody DeleteEmail deleteEmail
    );
}
