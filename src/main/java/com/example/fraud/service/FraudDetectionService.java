package com.example.fraud.service;

import com.example.fraud.model.Transaction;
import com.example.fraud.queue.spi.QueueService;
import com.example.fraud.queue.spi.QueueServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FraudDetectionService {

    private static final double HIGH_RISK_THRESHOLD = 5000.00;
    private static final double MEDIUM_RISK_THRESHOLD = 1000.00;

    private final QueueServiceFactory queueServiceFactory;

    @Value("${queue.service.provider}")
    private String queueServiceProvider;

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

        // Send transaction to the appropriate queue for processing
        sendToQueue(transaction);

        return analysisResult;
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

    /**
     * Sends the transaction to the appropriate queue for processing.
     *
     * @param transaction the transaction to send
     */
    private void sendToQueue(Transaction transaction) {
        try {
            QueueService queueService = queueServiceFactory.getQueueService(queueServiceProvider);
            queueService.initialize();

            // Simulating sending the transaction (actual implementation depends on queueService API)
            System.out.printf("Sending transaction ID: %s to %s queue%n", transaction.getTransactionId(), queueServiceProvider);
        } catch (Exception e) {
            System.err.printf("Failed to send transaction ID: %s to queue. Error: %s%n", transaction.getTransactionId(), e.getMessage());
        }
    }
}
