package ru.mamakapa.ememeemail.services.jpa;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.mamakapa.ememeemail.IntegrationEnvironment;
import ru.mamakapa.ememeemail.configurations.AWSConfiguration;
import ru.mamakapa.ememeemail.repositories.jpa.JpaStorageRepository;
import ru.mamakapa.ememeemail.services.EmailNotifier;
import ru.mamakapa.ememeemail.services.StorageService;
import ru.mamakapa.ememeemail.services.updateSenders.S3FileUploader;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.TemporalAmount;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class JpaStorageServiceTest extends IntegrationEnvironment {

    @Autowired
    JpaStorageService storageService;

    @Autowired
    JpaStorageRepository storageRepository;

    @MockBean
    AmazonS3 s3Client;

    @MockBean
    EmailNotifier emailNotifier;
    @MockBean
    S3FileUploader fileUploader;

    @MockBean
    AWSConfiguration awsConfiguration;

    @Test
    @Transactional
    @Rollback
    void store() {

        //when
        storageService.store("somefile.txt", Timestamp.from(Instant.now()));
        storageService.store("another.txt", Timestamp.from(Instant.now()));
        storageService.store("third.txt", Timestamp.from(Instant.now()));

        //then
        assertEquals(3, storageRepository.findAll().size());
        assertEquals("another.txt", storageRepository.findById("another.txt").get().getFileName());
    }


    @Test
    @Transactional
    @Rollback
    void storeSameFile(){
        //when
        storageService.store("somefile.txt", Timestamp.from(Instant.now()));
        storageService.store("somefile.txt", Timestamp.from(Instant.now()));

        //then
        assertEquals(1, storageRepository.findAll().size());
    }

    @Test
    @Transactional
    @Rollback
    void getAllAfter2HoursFromTime() {
        //given
        storageService.store("somefile.txt", Timestamp.from(Instant.now().minusMillis(7200000)));
        storageService.store("second.txt", Timestamp.from(Instant.now().minusMillis(8500000)));
        storageService.store("third.txt", Timestamp.from(Instant.now()));

        //when
        var files = storageService.getAllAfter2HoursFromTime(Timestamp.from(Instant.now()));

        //then
        assertEquals(2, files.size());
    }

    @Test
    @Transactional
    @Rollback
    void getEmptyFilesList(){
        //when
        var files = storageService.getAllAfter2HoursFromTime(Timestamp.from(Instant.now()));

        //then
        assertTrue(files.isEmpty());
    }
}