package ru.mamakapa.ememeemail.entities.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "storage")
public class StorageEntity {
    @Id
    @Column(name = "file_name")
    String fileName;
    @Column(name = "save_time")
    Timestamp saveTime;
}
