package ru.mamakapa.telegramBot.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.mamakapa.telegramBot.data.LetterToUser;
import ru.mamakapa.telegramBot.kafka.KafkaMessageConsumer;
import ru.mamakapa.telegramBot.service.LetterToUserHandler;

import java.util.HashMap;
import java.util.Map;

@ConditionalOnProperty(prefix = "kafka", name = "isEnabled", havingValue = "true")
@Configuration
public class KafkaConsumerConfig {
    @Bean
    KafkaMessageConsumer kafkaMessageConsumer(LetterToUserHandler letterToUserHandler) {
        return new KafkaMessageConsumer(letterToUserHandler);
    }

    @Bean
    public ConsumerFactory<Long, LetterToUser> consumerConfig(KafkaConfig kafkaConfig) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.serverUrl());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(properties);
    }
}
