package ru.mamakapa.telegramBot.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.mamakapa.telegramBot.data.LetterToUser;
import ru.mamakapa.telegramBot.kafka.KafkaMessageConsumer;
import ru.mamakapa.telegramBot.service.LetterToUserHandler;

import java.util.HashMap;
import java.util.Map;

@ConditionalOnProperty(prefix = "kafka", name = "isEnabled", havingValue = "true")
@Configuration
public class KafkaConsumerConfig {
    @Bean
    KafkaMessageConsumer kafkaMessageConsumer(KafkaConfig kafkaConfig, LetterToUserHandler letterToUserHandler) {
        return new KafkaMessageConsumer(kafkaTemplate(kafkaConfig), letterToUserHandler);
    }

    KafkaTemplate<Long, LetterToUser> kafkaTemplate(KafkaConfig kafkaConfig) {
        return new KafkaTemplate<>(consumerConfig(kafkaConfig));
    }

    public ProducerFactory<Long, LetterToUser> consumerConfig(KafkaConfig kafkaConfig) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.serverUrl());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(properties);
    }
}
