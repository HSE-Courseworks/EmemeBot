package ru.mamakapa.vkbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.netty.http.client.HttpClient;
import ru.mamakapa.ememeSenderFunctionality.bot.service.EmemeEmailHttpClient;

import java.time.Duration;

@Configuration
public class ClientConfig {
    private final static int TIMEOUT_RESPONSE = 10;

    @Bean
    EmemeEmailHttpClient ememeEmailHttpClient(ServiceConfig serviceConfig) {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builder()
                .clientAdapter(WebClientAdapter.forClient(
                        WebClient.builder()
                                .baseUrl(serviceConfig.emailUrl())
                                .clientConnector(new ReactorClientHttpConnector(HttpClient
                                        .create()
                                        .responseTimeout(Duration.ofSeconds(TIMEOUT_RESPONSE))
                                ))
                                .build()
                ))
                .build();
        return httpServiceProxyFactory.createClient(EmemeEmailHttpClient.class);
    }
}
