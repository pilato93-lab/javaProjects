package org.example;

public class Account {
    // 1. Private Fields (Encapsulation - Column Mapping)
    private String accountNumber;
    private String name;
    private double balance;
    private String pin;

    // 2. Default Constructor (Required by enterprise frameworks)
    public Account() {
    }

    // 3. Parameterized Constructor (To easily create account objects in code)
    public Account(String accountNumber, String name, double balance, String pin) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
        this.pin = pin;
    }

    // 4. Getters and Setters (The security gates for reading/writing data)
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    // 5. toString() Method (For easy printing and debugging in the terminal)
    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}