package ru.mamakapa.telegramBot.kafka;

import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import ru.mamakapa.telegramBot.data.LetterToUser;
import ru.mamakapa.telegramBot.service.LetterToUserHandler;

@EnableKafka
public class KafkaMessageConsumer {
    private final KafkaTemplate<Long, LetterToUser> kafkaTemplate;
    private final LetterToUserHandler letterToUserHandler;

    public KafkaMessageConsumer(
            KafkaTemplate<Long, LetterToUser> kafkaTemplate,
            LetterToUserHandler letterToUserHandler
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.letterToUserHandler = letterToUserHandler;
    }

    @KafkaListener(topics = "${kafka.topicName}")
    public void handleMessage(LetterToUser letterToUser) throws Exception {
        letterToUserHandler.handle(letterToUser);
    }
}
