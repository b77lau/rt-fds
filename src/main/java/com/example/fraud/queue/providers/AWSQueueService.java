package com.example.fraud.queue.providers;

import com.example.fraud.model.Transaction;
import com.example.fraud.queue.spi.QueueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

@RequiredArgsConstructor
public class AWSQueueService implements QueueService {

    private final SqsClient sqsClient;

    @Value("${aws.sqs.queue-url}")
    private String queueUrl;

    @Override
    public void initialize() {
        System.out.println("Initializing AWS SQS Client.");
    }

    @Override
    public Transaction receiveTransaction() {
        try {
            ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .maxNumberOfMessages(1)
                    .build();

            List<Message> messages = sqsClient.receiveMessage(request).messages();

            if (!messages.isEmpty()) {
                String body = messages.get(0).body();
                return new ObjectMapper().readValue(body, Transaction.class);
            }
        } catch (Exception e) {
            System.err.println("Error receiving SQS message: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void acknowledgeTransaction(String messageId) {
        System.out.println("Acknowledging AWS SQS message ID: " + messageId);
    }
}
