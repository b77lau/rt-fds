package com.example.fraud.model;

import java.time.LocalDateTime;

public class Transaction {

    private String id;
    private String accountNumber;
    private double amount;
    private LocalDateTime timestamp;

    // Default Constructor
    public Transaction() {
    }

    // Parameterized Constructor
    public Transaction(String id, String accountNumber, double amount, LocalDateTime timestamp) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getTransactionId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // toString Method
    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}
