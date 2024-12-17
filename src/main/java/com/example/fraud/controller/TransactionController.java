package com.example.fraud.controller;

import com.example.fraud.FraudDetectionApplication;
import com.example.fraud.model.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    private final FraudDetectionApplication fraudService;

    public TransactionController(FraudDetectionApplication fraudService) {
        this.fraudService = fraudService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<String> analyzeTransaction(@RequestBody Transaction txn) {
        boolean fraud = fraudService.isFraudulent(txn);
        return fraud ? ResponseEntity.status(400).body("Fraud Detected") :
                ResponseEntity.ok("Transaction Valid");
    }
}
