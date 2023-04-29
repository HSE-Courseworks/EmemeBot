package ru.mamakapa.ememeemail.entities;

import lombok.*;

import java.sql.Timestamp;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImapEmail {
    private Long id;
    private Long botId;
    private String email;
    private String appPassword;
    private String host;
    private Timestamp lastChecked;
    private Timestamp lastMessageTime;

    public ImapEmail(Long botId, String email, String appPassword, String host, Timestamp lastChecked, Timestamp lastMessageTime) {
        this.botId = botId;
        this.email = email;
        this.appPassword = appPassword;
        this.host = host;
        this.lastChecked = lastChecked;
        this.lastMessageTime = lastMessageTime;
    }
}
