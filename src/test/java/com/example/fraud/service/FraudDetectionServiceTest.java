package com.example.fraud.service;

import com.example.fraud.model.Transaction;
import com.example.fraud.queue.spi.QueueServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FraudDetectionServiceTest {

    private FraudDetectionService fraudDetectionService;
    private QueueServiceFactory mockQueueServiceFactory;

    @BeforeEach
    void setUp() {
        // Create a mock QueueServiceFactory
        mockQueueServiceFactory = Mockito.mock(QueueServiceFactory.class);

        // Initialize FraudDetectionService with the mock dependency
        fraudDetectionService = new FraudDetectionService(mockQueueServiceFactory);
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
}
