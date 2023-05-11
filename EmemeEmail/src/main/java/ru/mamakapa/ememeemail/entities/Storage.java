package ru.mamakapa.ememeemail.entities;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class Storage {
    String fileName;

    Timestamp saveTime;
}
