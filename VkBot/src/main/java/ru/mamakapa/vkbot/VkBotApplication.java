package ru.mamakapa.vkbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.mamakapa.vkbot.config.VkBotConfig;

@SpringBootApplication
@EnableConfigurationProperties(VkBotConfig.class)
public class VkBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(VkBotApplication.class, args);
    }
}
