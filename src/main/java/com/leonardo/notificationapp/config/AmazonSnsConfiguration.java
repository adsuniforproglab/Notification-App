package com.leonardo.notificationapp.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class AmazonSnsConfiguration {

    @Value("${aws.access.key}")
    private String accessKey;

    @Value("${aws.secret.key}")
    private String secretKey;

    @Value("${aws.region:us-east-1}")
    private String awsRegion;

    @Bean
    AWSCredentials awsCredentials() {
        log.debug("Initializing AWS credentials");
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    @Bean
    AmazonSNS amazonSNS() {
        log.info("Initializing Amazon SNS client with region: {}", awsRegion);
        return AmazonSNSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials()))
                .withRegion(Regions.fromName(awsRegion))
                .build();
    }
}
