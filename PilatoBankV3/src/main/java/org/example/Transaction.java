package org.example;

public class Transaction {

    // The variables - info that a receipt hold
    private String accountNumber;
    private  String transactionType;
    private  double amount;

    // Constructing a receipt for the transaction
    public Transaction(String accountNumber, String transactionType, double amount){
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    //database reading the receipt
    public  String getAccountNumber() {
        return accountNumber;
    }

    public  String getTransactionType() {
        return transactionType;
    }

    public  double getAmount() {
        return amount;
    }
}
