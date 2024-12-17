package com.example.fraud.providers;

import com.example.fraud.spi.MessageQueueProvider;
import com.google.cloud.pubsub.v1.Publisher;

public class GcpPubSubProvider implements MessageQueueProvider {

    private Publisher publisher;

    @Override
    public void connect() {
        System.out.println("Connected to GCP Pub/Sub");
        // Setup GCP Pub/Sub client here
    }

    @Override
    public String getQueueName() {
        return "gcp-topic-name";
    }
}
