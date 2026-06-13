package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String accountNumber = "";

        System.out.println("============================");
        System.out.println("Welcome to PILATO BANK V3");
        System.out.println("============================");

        System.out.println("Please enter your account number:");
        accountNumber = input.nextLine();

        ClientDAO clientDAO = new ClientDAO();
        Client client = clientDAO.getClientByAccountNumber(accountNumber);

        if (client == null) {
            System.out.println("❌ Account not found.");
        } else {

            System.out.println("Please enter your PIN:");
            String rawPinInput = input.nextLine();

            String newlyScrambledPin = SecurityUtils.hashPin(rawPinInput);

            String databaseScrambledPin = client.getPin();

            if (newlyScrambledPin.equals(databaseScrambledPin)) {
                System.out.println("✅ Access Granted.");
                System.out.println("Welcome back, " + client.getFirstName() + "!");

            } else {
                System.out.println("❌ Incorrect PIN. Access Denied.");
            }
        }

        input.close();
    }
}