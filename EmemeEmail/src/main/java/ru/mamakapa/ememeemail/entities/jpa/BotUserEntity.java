package ru.mamakapa.ememeemail.entities.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mamakapa.ememeemail.DTOs.requests.MessengerType;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "botuser")
public class BotUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "chatid")
    private Long chatId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private MessengerType type;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE
            },
            targetEntity = ImapEmailEntity.class)
    @JoinTable(
            name = "botemail",
            inverseJoinColumns = @JoinColumn(name = "email_id",
                    nullable = false),
            joinColumns = @JoinColumn(name = "botuser_id",
                    nullable = false)
    )
    private List<ImapEmailEntity> emails = new ArrayList<>();

    public BotUserEntity(Long chatId, MessengerType type) {
        this.chatId = chatId;
        this.type = type;
    }

    @PreRemove
    public void removeEmailsAssociations(){
        for (var email : emails){
            email.getUsers().remove(this);
        }
    }
}
