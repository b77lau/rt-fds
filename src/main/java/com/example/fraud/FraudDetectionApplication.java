package com.example.fraud;

import com.example.fraud.model.Transaction;

public class FraudDetectionApplication {
    private final double THRESHOLD = 10000.0;

    public boolean isFraudulent(Transaction transaction) {
        return transaction.getAmount() > THRESHOLD;
    }
}
