package ru.mamakapa.ememeemail.entities.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mamakapa.ememeemail.DTOs.requests.MessengerType;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "botuser")
public class BotUserEntity {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "chatId")
    private Long chatId;

    @Column(name = "type")
    private MessengerType type;

    @ManyToMany(mappedBy = "users")
    private List<ImapEmailEntity> emails;
}
