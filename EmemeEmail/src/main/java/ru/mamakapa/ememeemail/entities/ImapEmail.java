package ru.mamakapa.ememeemail.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class ImapEmail {
    Long id;
    Long botId;
    String email;
    String appPassword;
    String host;
    Timestamp lastChecked;
    Timestamp lastMessageTime;
}
