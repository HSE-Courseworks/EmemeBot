package ru.mamakapa.ememeemail.services.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mamakapa.ememeemail.entities.Storage;
import ru.mamakapa.ememeemail.entities.jpa.StorageEntity;
import ru.mamakapa.ememeemail.exceptions.NotFoundEmemeException;
import ru.mamakapa.ememeemail.repositories.jpa.JpaStorageRepository;
import ru.mamakapa.ememeemail.services.StorageService;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JpaStorageService implements StorageService {

    final JpaStorageRepository storageRepository;

    @Override
    @Transactional
    public void store(String fileName, Timestamp saveTime) {
        var storage = new StorageEntity(fileName, saveTime);
        storageRepository.save(storage);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Storage> getAllAfter2HoursFromTime(Timestamp fromTime) {
        var files = storageRepository.getAllFilesAfter2HoursFromTime(fromTime);
        return files.stream()
                .map(entity -> Storage.builder()
                        .fileName(entity.getFileName())
                        .saveTime(entity.getSaveTime())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String fileName) {
        try {
            storageRepository.deleteById(fileName);
        } catch (Exception e) {
            throw new NotFoundEmemeException(String.format("File %s not found", fileName));
        }
    }
}
