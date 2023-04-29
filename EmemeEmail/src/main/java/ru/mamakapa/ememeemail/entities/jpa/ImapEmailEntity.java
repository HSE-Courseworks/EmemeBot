package ru.mamakapa.ememeemail.entities.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "imapemail")
public class ImapEmailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "emails")
    private List<BotUserEntity> users = new ArrayList<>();

    public ImapEmailEntity(String address, String password, String host,
                           Timestamp lastChecked, Timestamp lastUpdated) {
        this.address = address;
        this.password = password;
        this.host = host;
        this.lastChecked = lastChecked;
        this.lastUpdated = lastUpdated;
    }
}
