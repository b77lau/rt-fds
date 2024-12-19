package com.example.fraud.controller;

import com.example.fraud.FraudDetectionApplication;
import com.example.fraud.model.Transaction;
import com.example.fraud.service.FraudDetectionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FraudDetectionApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FraudDetectionService fraudDetectionService;

    @Test
    void testAnalyzeTransaction_ValidTransaction() throws Exception {
        // JSON payload for a valid transaction
        String transactionJson = """
            {
                "id": "1",
                "accountNumber": "12345",
                "amount": 100.00,
                "timestamp": "2024-12-19T10:15:30"
            }
        """;

        // Mock response for a valid transaction
        Map<String, Object> mockResponse = Map.of(
                "transactionId", "1",
                "riskLevel", "LOW",
                "message", "Transaction is LOW risk."
        );

        when(fraudDetectionService.analyzeTransaction(any(Transaction.class))).thenReturn(mockResponse);

        // Perform POST request and validate the response
        mockMvc.perform(post("/transactions/analyze")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value("1"))
                .andExpect(jsonPath("$.riskLevel").value("LOW"))
                .andExpect(jsonPath("$.message").value("Transaction is LOW risk."));
    }

    @Test
    void testAnalyzeTransaction_InvalidPayload() throws Exception {
        // Invalid JSON payload
        String invalidJson = """
            {
                "id": "1",
                "accountNumber": "12345",
                "amount": "invalidAmount",
                "timestamp": "2024-12-19T10:15:30"
            }
        """;

        // Perform POST request and expect a Bad Request status
        mockMvc.perform(post("/transactions/analyze")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAnalyzeTransaction_HighRiskTransaction() throws Exception {
        // JSON payload for a high-risk transaction
        String highRiskTransactionJson = """
            {
                "id": "2",
                "accountNumber": "67890",
                "amount": 6000.00,
                "timestamp": "2024-12-19T10:15:30"
            }
        """;

        // Mock response for a high-risk transaction
        Map<String, Object> mockResponse = Map.of(
                "transactionId", "2",
                "riskLevel", "HIGH",
                "message", "Transaction flagged as HIGH risk."
        );

        when(fraudDetectionService.analyzeTransaction(any(Transaction.class))).thenReturn(mockResponse);

        // Perform POST request and validate the response
        mockMvc.perform(post("/transactions/analyze")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(highRiskTransactionJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value("2"))
                .andExpect(jsonPath("$.riskLevel").value("HIGH"))
                .andExpect(jsonPath("$.message").value("Transaction flagged as HIGH risk."));
    }
}
