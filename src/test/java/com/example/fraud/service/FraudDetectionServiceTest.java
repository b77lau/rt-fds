package com.example.fraud.service;

import com.example.fraud.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FraudDetectionServiceTest {

    private com.example.fraud.service.FraudDetectionService fraudDetectionService;

    @BeforeEach
    void setUp() {
        fraudDetectionService = new com.example.fraud.service.FraudDetectionService();
    }

    @Test
    void testAnalyzeTransaction_HighRisk() {
        Transaction transaction = new Transaction("T123", "ACC001", 6000.00, LocalDateTime.now());

        Map<String, Object> result = fraudDetectionService.analyzeTransaction(transaction);

        assertEquals("T123", result.get("transactionId"));
        assertEquals("HIGH", result.get("riskLevel"));
        assertEquals("Transaction flagged as HIGH risk.", result.get("message"));
    }

    @Test
    void testAnalyzeTransaction_MediumRisk() {
        Transaction transaction = new Transaction("T124", "ACC002", 1500.00, LocalDateTime.now());

        Map<String, Object> result = fraudDetectionService.analyzeTransaction(transaction);

        assertEquals("T124", result.get("transactionId"));
        assertEquals("MEDIUM", result.get("riskLevel"));
        assertEquals("Transaction flagged as MEDIUM risk.", result.get("message"));
    }

    @Test
    void testAnalyzeTransaction_LowRisk() {
        Transaction transaction = new Transaction("T125", "ACC003", 500.00, LocalDateTime.now());

        Map<String, Object> result = fraudDetectionService.analyzeTransaction(transaction);

        assertEquals("T125", result.get("transactionId"));
        assertEquals("LOW", result.get("riskLevel"));
        assertEquals("Transaction is LOW risk.", result.get("message"));
    }

    @Test
    void testIsFraudulent_HighRiskTransaction() {
        Transaction transaction = new Transaction("T126", "ACC004", 6000.00, LocalDateTime.now());

        boolean isFraudulent = fraudDetectionService.isFraudulent(transaction);

        assertTrue(isFraudulent, "Transaction should be marked as fraudulent for high-risk transactions.");
    }

    @Test
    void testIsFraudulent_LowRiskTransaction() {
        Transaction transaction = new Transaction("T127", "ACC005", 500.00, LocalDateTime.now());

        boolean isFraudulent = fraudDetectionService.isFraudulent(transaction);

        assertFalse(isFraudulent, "Transaction should not be marked as fraudulent for low-risk transactions.");
    }
}
