package org.example.controller;

public class TransactionRequest {
    private String accountNumber;
    private double amount;

    // Getters
    public String getAccountNumber() { return accountNumber; }
    public double getAmount() { return amount; }

    // Setters
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public void setAmount(double amount) { this.amount = amount; }
}