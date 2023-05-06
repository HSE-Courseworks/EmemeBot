package ru.mamakapa.ememeSenderFunctionality.bot.service;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.HttpExchange;
import ru.mamakapa.ememeSenderFunctionality.bot.data.EmailData;
import ru.mamakapa.ememeSenderFunctionality.bot.data.MessengerType;
import ru.mamakapa.ememeSenderFunctionality.bot.dto.AddEmail;
import ru.mamakapa.ememeSenderFunctionality.bot.dto.AllEmails;
import ru.mamakapa.ememeSenderFunctionality.bot.dto.DeleteEmail;

@HttpExchange("http://localhost:8080/")
public interface EmemeEmailHttpClient {
    @PostMapping("registerChat/{messengerType}/{chatId}")
    void registerChat(
            @PathVariable MessengerType messengerType,
            @PathVariable long chatId
    );

    @DeleteMapping("registerChat/{messengerType}/{chatId}")
    void unregisterChat(
            @PathVariable MessengerType messengerType,
            @PathVariable long chatId
    );

    @GetMapping("emails")
    AllEmails getAllEmails(
            @RequestParam MessengerType messengerType,
            @RequestParam long chatId);

    @PostMapping("emails")
    EmailData addEmail(
            @RequestParam MessengerType messengerType,
            @RequestParam long chatId,
            @RequestBody AddEmail addEmail
    );

    @DeleteMapping("emails")
    EmailData deleteEmail(
            @RequestParam MessengerType messengerType,
            @RequestParam long chatId,
            @RequestBody DeleteEmail deleteEmail
    );
}
