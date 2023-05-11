package ru.mamakapa.ememeemail.configurations;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mamakapa.ememeemail.services.updateSenders.HttpUpdateSender;
import ru.mamakapa.ememeemail.services.TgBotClient;
import ru.mamakapa.ememeemail.services.updateSenders.UpdateSender;
import ru.mamakapa.ememeemail.services.VkBotClient;

@Configuration
@ConditionalOnProperty(prefix = "kafka", name = "isEnabled", havingValue = "false")
public class HttpUpdateSenderConfig {

    @Bean
    UpdateSender httpUpdateSender(TgBotClient tgBotClient, VkBotClient vkBotClient) {
        return new HttpUpdateSender(tgBotClient, vkBotClient);
    }
}
