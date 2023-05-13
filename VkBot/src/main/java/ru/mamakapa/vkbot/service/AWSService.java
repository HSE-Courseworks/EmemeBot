package ru.mamakapa.vkbot.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import ru.mamakapa.vkbot.config.AWSConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class AWSService {
    private final AmazonS3Client s3Client;
    private final AWSConfig config;

    public AWSService(AmazonS3Client s3Client, AWSConfig config) {
        this.s3Client = s3Client;
        this.config = config;
    }

    public File downloadFile(String fileKey, Path directoryPath) throws IOException {
        S3Object object = s3Client.getObject(config.bucketName(), fileKey);
        File resFile = new File(directoryPath.toString() + object);
        FileUtils.copyInputStreamToFile(object.getObjectContent(), resFile);
        return resFile;
    }
}
