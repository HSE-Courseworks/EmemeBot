package ru.mamakapa.ememeemail.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import ru.mamakapa.ememeSenderFunctionality.bot.service.FileSender;
import ru.mamakapa.ememeemail.services.TgBotClient;
import ru.mamakapa.ememeemail.services.VkBotClient;
import ru.mamakapa.telegramBot.service.TelegramFileSender;
import ru.mamakapa.vkbot.service.VkFileSender;

import java.time.Duration;

@Configuration
public class ClientConfiguration {
    @Bean
    public VkBotClient vkBotClient(ApplicationConfig appConfig){
        WebClient webClient = WebClient.builder()
                .baseUrl(appConfig.vkBotBaseUrl())
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(webClient))
                .blockTimeout(Duration.ofSeconds(10, 10))
                .build();
        return factory.createClient(VkBotClient.class);
    }

    @Bean
    public TgBotClient tgBotClient(ApplicationConfig appConfig){
        WebClient webClient = WebClient.builder()
                .baseUrl(appConfig.tgBotBaseUrl())
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(webClient))
                .blockTimeout(Duration.ofSeconds(10, 10))
                .build();
        return factory.createClient(TgBotClient.class);
    }

    @Bean
    public VkFileSender vkFileSender(ApplicationConfig appConfig){
        return new VkFileSender(appConfig.fileSender().vkToken());
    }

    @Bean
    public TelegramFileSender telegramFileSender(ApplicationConfig appConfig){
        return new TelegramFileSender(appConfig.fileSender().tgToken());
    }

}
