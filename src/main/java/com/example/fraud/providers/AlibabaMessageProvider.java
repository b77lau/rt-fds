package com.example.fraud.providers;

import com.example.fraud.spi.MessageQueueProvider;

public class AlibabaMessageProvider implements MessageQueueProvider {

    @Override
    public void connect() {
        System.out.println("Connected to Alibaba Message Service");
        // Setup Alibaba client here
    }

    @Override
    public String getQueueName() {
        return "alibaba-queue-name";
    }
}
