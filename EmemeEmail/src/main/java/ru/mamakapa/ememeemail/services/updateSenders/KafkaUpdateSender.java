package ru.mamakapa.ememeemail.services.updateSenders;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import ru.mamakapa.ememeemail.DTOs.requests.LetterContent;
import ru.mamakapa.ememeemail.entities.BotUser;

@RequiredArgsConstructor
public class KafkaUpdateSender implements UpdateSender {

    final KafkaTemplate<Long, LetterContent> kafkaTemplate;

    private final static String VK_TOPIC_NAME = "VkUpdate";
    private final static String TELEGRAM_TOPIC_NAME = "TelegramUpdate";

    @Override
    public void sendUpdate(BotUser user, LetterContent content) {
        switch (user.getMessengerType()) {
            case VK -> kafkaTemplate.send(VK_TOPIC_NAME, content);
            case TG -> kafkaTemplate.send(TELEGRAM_TOPIC_NAME, content);
        }
    }
}
