package ru.mamakapa.ememebot.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Folder;
import javax.mail.Store;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImapConfig {
    @Value("${mail.user}")
    private String username;
    @Value("${mail.password}")
    private String password;
    @Value("${mail.host}")
    private String host;

    private Store store;
    private Folder inbox;
    private int messageCount;
    private boolean isConnected;
}
