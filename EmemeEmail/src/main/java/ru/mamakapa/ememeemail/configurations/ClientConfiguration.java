package ru.mamakapa.ememeemail.configurations;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import ru.mamakapa.ememeemail.services.TgBotClient;
import ru.mamakapa.ememeemail.services.VkBotClient;

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
    DbxClientV2 dropboxClient(ApplicationConfig appConfig){
        var config = DbxRequestConfig.newBuilder(appConfig.dropbox().clientId()).build();
        return new DbxClientV2(config, appConfig.dropbox().token());
    }
}
