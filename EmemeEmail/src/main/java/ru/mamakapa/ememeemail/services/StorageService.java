package ru.mamakapa.ememeemail.services;

import ru.mamakapa.ememeemail.entities.Storage;

import java.sql.Timestamp;
import java.util.List;

public interface StorageService {
    void store(String fileName, Timestamp saveTime);
    List<Storage> getAllAfter2HoursFromTime(Timestamp fromTime);
    void delete(String fileName);
}
