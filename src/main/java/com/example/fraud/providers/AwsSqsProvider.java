package com.example.fraud.providers;

import com.example.fraud.spi.MessageQueueProvider;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;

public class AwsSqsProvider implements MessageQueueProvider {

    private SqsClient sqsClient;

    @Override
    public void connect() {
        sqsClient = SqsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
        System.out.println("Connected to AWS SQS");
    }

    @Override
    public String getQueueName() {
        return "aws-queue-name";
    }
}
