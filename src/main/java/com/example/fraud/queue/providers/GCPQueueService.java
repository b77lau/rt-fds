package com.example.fraud.queue.providers;

import com.example.fraud.model.Transaction;
import com.example.fraud.queue.spi.QueueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;

import java.util.concurrent.CountDownLatch;

public class GCPQueueService implements QueueService {

    private final String projectId;
    private final String subscriptionId;

    public GCPQueueService(String projectId, String subscriptionId) {
        this.projectId = projectId;
        this.subscriptionId = subscriptionId;
    }

    @Override
    public void initialize() {
        System.out.println("Initializing GCP Pub/Sub Client.");
    }

    @Override
    public Transaction receiveTransaction() {
        final CountDownLatch latch = new CountDownLatch(1);
        final Transaction[] transaction = new Transaction[1];

        ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(projectId, subscriptionId);

        Subscriber subscriber = Subscriber.newBuilder(subscriptionName, (PubsubMessage message, AckReplyConsumer consumer) -> {
            try {
                String data = message.getData().toStringUtf8();
                transaction[0] = new ObjectMapper().readValue(data, Transaction.class);
                consumer.ack();
                latch.countDown();
            } catch (Exception e) {
                consumer.nack();
                System.err.println("Error processing Pub/Sub message: " + e.getMessage());
            }
        }).build();

        subscriber.startAsync().awaitRunning();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return transaction[0];
    }

    @Override
    public void acknowledgeTransaction(String messageId) {
        System.out.println("Acknowledging GCP Pub/Sub message ID: " + messageId);
    }
}
