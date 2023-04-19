package ru.mamakapa.ememeemail.entities;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
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
}
