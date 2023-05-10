package ru.mamakapa.ememeemail.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mamakapa.ememeemail.entities.jpa.StorageEntity;

import java.sql.Timestamp;
import java.util.List;

public interface JpaStorageRepository extends JpaRepository<StorageEntity, String> {
    @Query(value = "SELECT * FROM storage as s WHERE :cur_time - s.save_time >= INTERVAL '2' HOUR",
            nativeQuery = true)
    List<StorageEntity> getAllFilesAfter2HoursFromTime(@Param("cur_time") Timestamp timestamp);
}
