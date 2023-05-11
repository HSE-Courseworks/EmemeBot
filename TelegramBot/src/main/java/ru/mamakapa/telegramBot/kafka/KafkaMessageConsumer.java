package ru.mamakapa.telegramBot.kafka;

import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import ru.mamakapa.telegramBot.data.LetterToUser;
import ru.mamakapa.telegramBot.service.LetterToUserHandler;

@EnableKafka
public class KafkaMessageConsumer {
    private final LetterToUserHandler letterToUserHandler;

    public KafkaMessageConsumer(LetterToUserHandler letterToUserHandler) {
        this.letterToUserHandler = letterToUserHandler;
    }

    @KafkaListener(topics = "${kafka.topicName}")
    public void handleMessage(LetterToUser letterToUser) throws Exception {
        letterToUserHandler.handle(letterToUser);
    }
}
