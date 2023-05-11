package ru.mamakapa.telegramBot.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
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
    public KafkaListenerContainerFactory<?> kafkaListenerContainerFactory(KafkaConfig kafkaConfig) {
        ConcurrentKafkaListenerContainerFactory<Long, LetterToUser> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerConfig(kafkaConfig));
        return factory;
    }

    public ConsumerFactory<Long, LetterToUser> consumerConfig(KafkaConfig kafkaConfig) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.serverUrl());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfig.groupId());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(properties);
    }
}
