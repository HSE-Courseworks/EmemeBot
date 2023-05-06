package ru.mamakapa.vkbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import ru.mamakapa.ememeSenderFunctionality.bot.service.EmemeEmailHttpClient;

@Configuration
public class ClientConfig {
    @Bean
    EmemeEmailHttpClient ememeEmailHttpClient(){
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builder()
                .clientAdapter(WebClientAdapter.forClient(
                        WebClient.builder().build()
                ))
                .build();
        return httpServiceProxyFactory.createClient(EmemeEmailHttpClient.class);
    }
}
