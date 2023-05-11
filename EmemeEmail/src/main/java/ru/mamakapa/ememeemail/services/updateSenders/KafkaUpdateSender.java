package ru.mamakapa.ememeemail.services.updateSenders;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import ru.mamakapa.ememeemail.DTOs.requests.LetterContent;
import ru.mamakapa.ememeemail.entities.BotUser;

@RequiredArgsConstructor
public class KafkaUpdateSender implements UpdateSender {

    final KafkaTemplate<Long, LetterContent> kafkaTemplate;

    @Value("${kafka.vkBotTopic}")
    private String vkTopicName;
    @Value("${kafka.tgBotTopic}")
    private String tgTopicName;

    @Override
    public void sendUpdate(BotUser user, LetterContent content) {
        switch (user.getMessengerType()) {
            case VK -> kafkaTemplate.send(vkTopicName, content);
            case TG -> kafkaTemplate.send(tgTopicName, content);
        }
    }
}
