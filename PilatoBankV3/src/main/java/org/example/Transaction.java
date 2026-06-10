package org.example;

import java.sql.Timestamp;

public class Transaction {
    private int id;
    private String accountNumber;
    private String transactionType;
    private double amount;
    private Timestamp timestamp;

    // 1. Default Constructor (Required by frameworks)
    public Transaction() {}

    // 2. Parameterized Constructor (For creating new logs)
    public Transaction(String accountNumber, String transactionType, double amount) {
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    // 3. Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }

    // 4. toString method for clean terminal printing
    @Override
    public String toString() {
        return "Transaction{" +
                "accountNumber='" + accountNumber + '\'' +
                ", type='" + transactionType + '\'' +
                ", amount=" + amount +
                ", time=" + timestamp +
                '}';
    }
}