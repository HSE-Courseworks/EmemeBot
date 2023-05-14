package ru.mamakapa.telegramBot.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfiguration {
    private final AWSConfig config;

    public AWSConfiguration(AWSConfig config) {
        this.config = config;
    }

    public AWSCredentials credentials() {
        return new BasicAWSCredentials(config.accessKey(), config.secretKey());
    }

    @Bean
    public AmazonS3Client s3Client() {

        return (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials()))
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(config.endpoint(), config.region())
                )
                .build();
    }
}