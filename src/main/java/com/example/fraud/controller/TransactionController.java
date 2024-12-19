package com.example.fraud.controller;

import com.example.fraud.model.Transaction;
import com.example.fraud.service.FraudDetectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final FraudDetectionService fraudDetectionService;

    public TransactionController(FraudDetectionService fraudDetectionService) {
        this.fraudDetectionService = fraudDetectionService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<Map<String, Object>> analyzeTransaction(@RequestBody Transaction transaction) {
        Map<String, Object> result = fraudDetectionService.analyzeTransaction(transaction);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addTransaction(@RequestBody Transaction transaction) {
        // Logic to persist the transaction (if needed)
        return new ResponseEntity<>("Transaction added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/status/{transactionId}")
    public ResponseEntity<Map<String, Object>> getTransactionStatus(@PathVariable String transactionId) {
        // Placeholder logic to retrieve the transaction status
        Map<String, Object> response = new HashMap<>();
        response.put("transactionId", transactionId);
        response.put("status", "Processed");

        return ResponseEntity.ok(response);
    }
}
