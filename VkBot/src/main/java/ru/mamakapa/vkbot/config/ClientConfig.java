package ru.mamakapa.vkbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import ru.mamakapa.ememeSenderFunctionality.bot.service.EmemeEmailHttpClient;

import java.time.Duration;

@Configuration
public class ClientConfig {
    private final static int TIMEOUT_RESPONSE_IN_SECONDS = 10;

    @Bean
    EmemeEmailHttpClient ememeEmailHttpClient(ServiceConfig serviceConfig) {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builder()
                .clientAdapter(WebClientAdapter.forClient(
                        WebClient.builder()
                                .baseUrl(serviceConfig.emailUrl())
                                .build()
                ))
                .blockTimeout(Duration.ofSeconds(TIMEOUT_RESPONSE_IN_SECONDS))
                .build();
        return httpServiceProxyFactory.createClient(EmemeEmailHttpClient.class);
    }
}
