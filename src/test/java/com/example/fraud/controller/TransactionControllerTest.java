package com.example.fraud.controller;

import com.example.fraud.FraudDetectionApplication;
import com.example.fraud.model.Transaction;
import com.example.fraud.service.FraudDetectionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FraudDetectionApplication.class) // Explicitly point to main application
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FraudDetectionService fraudDetectionService;

    @Autowired
    private ObjectMapper objectMapper;

    private Transaction validTransaction;

    @BeforeEach
    void setUp() {
        validTransaction = new Transaction("txn123", "ACC987654321", 15000.0, LocalDateTime.now());
    }

    @Test
    void testAnalyzeTransaction_Fraudulent() throws Exception {
        // Mock FraudDetectionService to return true for fraud
        when(fraudDetectionService.isFraudulent(any(Transaction.class))).thenReturn(true);

        mockMvc.perform(post("/analyze")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validTransaction)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Fraud Detected"));
    }
}
