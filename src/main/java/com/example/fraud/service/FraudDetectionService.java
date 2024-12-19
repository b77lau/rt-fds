package com.example.fraud.service;

import com.example.fraud.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FraudDetectionService {

    private static final double HIGH_RISK_THRESHOLD = 5000.00;
    private static final double MEDIUM_RISK_THRESHOLD = 1000.00;

    /**
     * Analyzes the transaction and provides a detailed risk assessment.
     *
     * @param transaction the transaction to analyze
     * @return a map containing the analysis results
     */
    public Map<String, Object> analyzeTransaction(Transaction transaction) {
        Map<String, Object> analysisResult = new HashMap<>();
        analysisResult.put("transactionId", transaction.getTransactionId());

        if (transaction.getAmount() >= HIGH_RISK_THRESHOLD) {
            analysisResult.put("riskLevel", "HIGH");
            analysisResult.put("message", "Transaction flagged as HIGH risk.");
            takeAction(transaction, "HIGH");
        } else if (transaction.getAmount() >= MEDIUM_RISK_THRESHOLD) {
            analysisResult.put("riskLevel", "MEDIUM");
            analysisResult.put("message", "Transaction flagged as MEDIUM risk.");
            takeAction(transaction, "MEDIUM");
        } else {
            analysisResult.put("riskLevel", "LOW");
            analysisResult.put("message", "Transaction is LOW risk.");
        }

        return analysisResult;
    }

    /**
     * Determines if a transaction is fraudulent.
     *
     * @param transaction the transaction to evaluate
     * @return true if the transaction is considered fraudulent, false otherwise
     */
    public boolean isFraudulent(Transaction transaction) {
        // Define logic to determine if a transaction is fraudulent
        return transaction.getAmount() >= HIGH_RISK_THRESHOLD;
    }

    /**
     * Takes appropriate action based on the risk level.
     *
     * @param transaction the transaction to act on
     * @param riskLevel   the assessed risk level
     */
    private void takeAction(Transaction transaction, String riskLevel) {
        switch (riskLevel) {
            case "HIGH":
                System.out.printf("Action: Freezing account for transaction ID: %s%n", transaction.getTransactionId());
                break;
            case "MEDIUM":
                System.out.printf("Action: Notifying customer for transaction ID: %s%n", transaction.getTransactionId());
                break;
            case "LOW":
                System.out.printf("Action: Monitoring transaction ID: %s%n", transaction.getTransactionId());
                break;
            default:
                throw new IllegalArgumentException("Unknown risk level: " + riskLevel);
        }
    }
}
