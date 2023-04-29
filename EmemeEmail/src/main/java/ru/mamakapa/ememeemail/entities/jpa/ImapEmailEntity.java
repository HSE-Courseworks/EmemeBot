package ru.mamakapa.ememeemail.entities.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "imapemail")
public class ImapEmailEntity {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "address")
    private String address;
    @Column(name = "password")
    private String password;
    @Column(name = "host")
    private String host;
    @Column(name = "lastchecked")
    private Timestamp lastChecked;
    @Column(name = "lastupdated")
    private Timestamp lastUpdated;

    @ManyToMany
    private List<BotUserEntity> users;
}
