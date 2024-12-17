package com.example.fraud.service;

import com.example.fraud.FraudDetectionApplication;
import com.example.fraud.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FraudDetectionServiceTest {

    private FraudDetectionApplication fraudDetectionService;

    @BeforeEach
    void setUp() {
        fraudDetectionService = new FraudDetectionApplication();
    }

    @Test
    void testTransactionIsFraudulent_WhenAmountExceedsThreshold() {
        // Arrange
        Transaction transaction = new Transaction("txn123", "ACC987654321", 15000.0, LocalDateTime.now());

        // Act
        boolean result = fraudDetectionService.isFraudulent(transaction);

        // Assert
        assertTrue(result, "Transaction should be marked as fraudulent if amount exceeds the threshold.");
    }

    @Test
    void testTransactionIsNotFraudulent_WhenAmountIsBelowThreshold() {
        // Arrange
        Transaction transaction = new Transaction("txn456", "ACC123456789", 9000.0, LocalDateTime.now());

        // Act
        boolean result = fraudDetectionService.isFraudulent(transaction);

        // Assert
        assertFalse(result, "Transaction should not be marked as fraudulent if amount is below the threshold.");
    }

    @Test
    void testTransactionIsFraudulent_WhenAmountEqualsThreshold() {
        // Arrange
        Transaction transaction = new Transaction("txn789", "ACC123456789", 10000.0, LocalDateTime.now());

        // Act
        boolean result = fraudDetectionService.isFraudulent(transaction);

        // Assert
        assertFalse(result, "Transaction should not be marked as fraudulent if amount equals the threshold.");
    }
}
