package ru.mamakapa.ememeemail.services.updateSenders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.mamakapa.ememeemail.DTOs.requests.LetterContent;
import ru.mamakapa.ememeemail.entities.BotUser;
import ru.mamakapa.ememeemail.services.TgBotClient;
import ru.mamakapa.ememeemail.services.VkBotClient;

@Slf4j
@RequiredArgsConstructor
public class HttpUpdateSender implements UpdateSender {

    final TgBotClient tgBotClient;
    final VkBotClient vkBotClient;

    @Override
    public void sendUpdate(BotUser user, LetterContent content) {
        log.info("Sending update to {} with mesType = {}", user.getChatId(), user.getMessengerType());
        try {
            switch (user.getMessengerType()){
                case TG -> tgBotClient.sendUpdateToTgBot(user.getChatId(), content);
                case VK -> vkBotClient.sendUpdateToVkBot(user.getChatId(), content);
            }
        } catch (RuntimeException exception){
            log.info("Exception in sendUpdate method! Message: {}", exception.getMessage());
        }
    }
}
