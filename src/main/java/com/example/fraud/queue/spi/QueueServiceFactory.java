package com.example.fraud.queue.spi;

import com.example.fraud.queue.providers.AWSQueueService;
import com.example.fraud.queue.providers.GCPQueueService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class QueueServiceFactory {

    @Value("${queue.provider}")
    private String provider;

    @Value("${aws.sqs.queue-url}")
    private String awsQueueUrl;

    @Value("${gcp.project-id}")
    private String gcpProjectId;

    @Value("${gcp.subscription-id}")
    private String gcpSubscriptionId;

    private final SqsClient sqsClient;

    public QueueServiceFactory(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    @Bean
    public QueueService queueService() {
        switch (provider.toLowerCase()) {
            case "aws":
                return new AWSQueueService(sqsClient);
            case "gcp":
                return new GCPQueueService(gcpProjectId, gcpSubscriptionId);
            default:
                throw new IllegalArgumentException("Unsupported provider: " + provider);
        }
    }
}
