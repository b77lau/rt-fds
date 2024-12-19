package com.example.fraud.service;

import com.example.fraud.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class FraudDetectionService {

    private static final double FRAUD_THRESHOLD = 10000.0;

    /**
     * Analyzes a transaction to determine if it is fraudulent.
     *
     * @param transaction The transaction to analyze.
     * @return True if the transaction is fraudulent; false otherwise.
     */
    public boolean isFraudulent(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        // Rule: Transaction is fraudulent if the amount exceeds the threshold
        return transaction.getAmount() > FRAUD_THRESHOLD;
    }
}
