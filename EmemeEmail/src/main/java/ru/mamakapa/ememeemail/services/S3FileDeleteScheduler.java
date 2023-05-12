package ru.mamakapa.ememeemail.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static java.sql.Timestamp.from;
import static java.time.Instant.now;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3FileDeleteScheduler {

    private static final long DELAY_TIME = 7200000;

    private final AmazonS3 s3Client;

    private final StorageService storageService;

    @Value("${aws.bucketName}")
    private String bucketName;

    @Scheduled(fixedDelay = DELAY_TIME)
    public void deleteFilesFromStorage() {
        var files = storageService.getAllAfter2HoursFromTime(from(now()));
        for (var file : files) {
            try {
                s3Client.deleteObject(new DeleteObjectRequest(bucketName, file.getFileName()));
                storageService.delete(file.getFileName());
                log.info("{} was deleted from storage", file.getFileName());
            } catch (Exception e) {
                log.info("Error deleting file {}\nException: {}", file.getFileName(), e.getMessage());
            }
        }
    }
}
