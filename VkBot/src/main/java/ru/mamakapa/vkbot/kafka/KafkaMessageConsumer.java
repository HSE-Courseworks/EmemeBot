package ru.mamakapa.vkbot.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import ru.mamakapa.vkbot.data.LetterToUser;
import ru.mamakapa.vkbot.service.LetterToUserHandler;

@EnableKafka
@Slf4j
public class KafkaMessageConsumer {
    private final LetterToUserHandler letterToUserHandler;

    public KafkaMessageConsumer(LetterToUserHandler letterToUserHandler) {
        this.letterToUserHandler = letterToUserHandler;
    }

    @KafkaListener(topics = "${kafka.topicName}")
    public void listen(LetterToUser letterToUser) {
        try {
            letterToUserHandler.handle(letterToUser);
        } catch (Exception e) {
            log.error("Handle letterToUser: {} with error: {}", letterToUser.toString(), e.getMessage());
        }
    }
}
