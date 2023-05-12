package ru.mamakapa.vkbot.config;

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
import ru.mamakapa.vkbot.data.LetterToUser;
import ru.mamakapa.vkbot.kafka.KafkaMessageConsumer;
import ru.mamakapa.vkbot.service.LetterToUserHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(prefix = "kafka", name = "isEnabled", havingValue = "true")
public class KafkaConsumerConfiguration {
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
        JsonDeserializer<LetterToUser> deserializer = new JsonDeserializer<>();
        deserializer.addTrustedPackages("*");
        deserializer.setRemoveTypeHeaders(false);
        deserializer.setUseTypeMapperForKey(true);

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.serverUrl());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfig.groupId());

        return new DefaultKafkaConsumerFactory<>(properties, new LongDeserializer(), deserializer);
    }
}
