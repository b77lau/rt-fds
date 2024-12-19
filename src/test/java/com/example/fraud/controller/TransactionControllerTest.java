package com.example.fraud.controller;

import com.example.fraud.FraudDetectionApplication;
import com.example.fraud.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = FraudDetectionApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAnalyzeTransaction_ValidTransaction() throws Exception {
        // JSON payload for the transaction
        String transactionJson = """
            {
                "id": "1",
                "accountNumber": "12345",
                "amount": 100.00,
                "timestamp": "2024-12-19T10:15:30"
            }
        """;

        // Perform POST request and validate the response
        mockMvc.perform(post("/transactions/analyze")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fraudulent").value(false)); // Assuming the response includes a 'fraudulent' field
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

        // Perform POST request and expect bad request status
        mockMvc.perform(post("/transactions/analyze")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}
