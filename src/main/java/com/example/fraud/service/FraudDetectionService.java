package com.example.fraud.service;

import com.example.fraud.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class FraudDetectionService {

    public void analyzeTransaction(Transaction transaction) {
        System.out.println("Analyzing transaction: " + transaction);

        if (isFraudulent(transaction)) {
            System.out.println("ALERT: Fraudulent transaction detected! Transaction ID: "
                    + transaction.getTransactionId());
        } else {
            System.out.println("Transaction is legitimate. Transaction ID: "
                    + transaction.getTransactionId());
        }
    }

    private boolean isFraudulent(Transaction transaction) {
        // Dummy fraud detection logic for demonstration purposes
        return transaction.getAmount() > 10000;
    }
}
