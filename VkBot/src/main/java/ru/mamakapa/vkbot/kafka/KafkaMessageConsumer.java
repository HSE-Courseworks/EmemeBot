package ru.mamakapa.vkbot.kafka;

import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import ru.mamakapa.vkbot.data.LetterToUser;
import ru.mamakapa.vkbot.service.LetterToUserHandler;

@EnableKafka
public class KafkaMessageConsumer {
    private final LetterToUserHandler letterToUserHandler;

    public KafkaMessageConsumer(LetterToUserHandler letterToUserHandler) {
        this.letterToUserHandler = letterToUserHandler;
    }

    @KafkaListener(topics = "${kafka.topicName}")
    public void listen(LetterToUser letterToUser) throws Exception {
        letterToUserHandler.handle(letterToUser);
    }
}
