package com.example.fraud.queue.spi;

import com.example.fraud.model.Transaction;

public interface QueueService {
    void initialize();
    Transaction receiveTransaction();
    void acknowledgeTransaction(String messageId);
}
