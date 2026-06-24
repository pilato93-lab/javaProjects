package org.example.controller;

public class LoginRequest {
    private String accountNumber;
    private String pin;

    // How Java code reads the data from the tray
    public String getAccountNumber() { return accountNumber; }
    public String getPin() { return pin; }

    // How the web engine drops the internet data into the tray
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public void setPin(String pin) { this.pin = pin; }
}