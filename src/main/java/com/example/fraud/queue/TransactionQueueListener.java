package com.example.fraud.queue;

import com.example.fraud.model.Transaction;
import com.example.fraud.service.FraudDetectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TransactionQueueListener {

    private final FraudDetectionService fraudDetectionService;
    private final SqsClient sqsClient;

    @Value("${aws.sqs.queue-url}")
    private String queueUrl;

    /**
     * Poll the message queue every 5 seconds.
     */
    @Scheduled(fixedRate = 5000)
    public void pollQueue() {
        try {
            // Receive messages from SQS
            ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .maxNumberOfMessages(10)
                    .waitTimeSeconds(10)
                    .build();

            List<Message> messages = sqsClient.receiveMessage(request).messages();

            for (Message message : messages) {
                try {
                    // Process the transaction
                    Transaction transaction = parseMessage(message.body());
                    fraudDetectionService.analyzeTransaction(transaction);

                    // Delete the message from the queue after processing
                    deleteMessage(message);
                } catch (Exception e) {
                    System.err.println("Error processing message: " + e.getMessage());
                }
            }
        } catch (SqsException e) {
            System.err.println("SQS Polling Error: " + e.awsErrorDetails().errorMessage());
        }
    }

    private Transaction parseMessage(String body) {
        // Use Jackson to parse the message body into a Transaction object
        return new com.fasterxml.jackson.databind.ObjectMapper()
                .readValue(body, Transaction.class);
    }

    private void deleteMessage(Message message) {
        DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
                .queueUrl(queueUrl)
                .receiptHandle(message.receiptHandle())
                .build();
        sqsClient.deleteMessage(deleteRequest);
    }
}
