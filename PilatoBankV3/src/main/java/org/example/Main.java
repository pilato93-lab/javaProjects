package org.example;

import java.util.Scanner;
import java.util.Locale;
import java.util.List;
import java.util.InputMismatchException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        Scanner input = new Scanner(System.in);
        input.useLocale(Locale.US);
        ClientDAO clientDAO = new ClientDAO();
        TransactionDAO transactionDAO = new TransactionDAO(); // handles logging

        System.out.println("=================================");
        System.out.println("    WELCOME TO PILATO BANK V3    ");
        System.out.println("=================================");

        System.out.print("Enter your Account Number: ");
        String accountNumber = input.nextLine();

        Client client = clientDAO.getClientByAccountNumber(accountNumber);

        if (client == null) {
            System.out.println("❌ Error: Account number not found.");
            return;
        }

        System.out.print("Enter your PIN: ");
        String enteredPin = input.nextLine();

        String hashedInputPin = SecurityUtils.hashPin(enteredPin);

        if (!hashedInputPin.equals(client.getPin())) {
            System.out.println("❌ Error: Invalid PIN. Access Denied.");
            return;
        }

        System.out.println("Login Successful! Welcome back, " + client.getName() + " [" + client.getAccountType() + " ACCOUNT]");
        ServiceTester.runIntegrityChecks(client);

        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- PILATO BANK MAIN MENU ---");
            System.out.println("1. Check Account Balance");
            System.out.println("2. Deposit Funds");
            System.out.println("3. Withdraw Funds");
            System.out.println("4. View Transaction History");
            System.out.println("5. Exit Session");
            System.out.print("Select an option (1-5): ");

            int choice = 0;
            try {
                choice = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter a valid number (1-5).");
                input.nextLine();
                continue; // Restart the loop to show the menu again
            }

            switch (choice) {
                case 1:
                    System.out.println("\n💰 Balance: R" + client.getBalance());
                    break;

                case 2:
                    System.out.print("\nEnter amount to deposit: R");
                    double depositAmount = 0;
                    try {
                        depositAmount = input.nextDouble();
                        input.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("❌ Invalid amount. Please enter numbers only.");
                        input.nextLine();
                        break;
                    }

                    if (depositAmount > 0) {
                        double newBalance = client.getBalance() + depositAmount;

                        if (clientDAO.updateBalance(client.getAccountNumber(), newBalance)) {
                            client.setBalance(newBalance);
                            transactionDAO.logTransaction(client.getAccountNumber(), depositAmount, "DEPOSIT");

                            System.out.println("✅ Deposit successful!");
                            System.out.println("💰 Updated Balance: R" + client.getBalance());
                        } else {
                            System.out.println("❌ Transaction failed due to database error.");
                        }
                    } else {
                        System.out.println("❌ Invalid amount. Deposit must be greater than R0.");
                    }
                    break;

                case 3:
                    System.out.print("\nEnter amount to withdraw: R");
                    double withdrawAmount = 0;
                    try {
                        withdrawAmount = input.nextDouble();
                        input.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("❌ Invalid amount. Please enter numbers only.");
                        input.nextLine();
                        break;
                    }

                    if (withdrawAmount <= 0) {
                        System.out.println("❌ Invalid amount. Withdrawal must be greater than R0.");
                        break;
                    }
                    if (withdrawAmount > client.getBalance()) {
                        System.out.println("❌ Insufficient Funds. Your balance is R" + client.getBalance());
                        break;
                    }

                    System.out.print("🔒 Security Check - Please re-enter your PIN to authorize withdrawal: ");
                    String confirmPin = input.nextLine();
                    String hashedConfirmPin = SecurityUtils.hashPin(confirmPin);

                    if (hashedConfirmPin.equals(client.getPin())) {
                        double newBalance = client.getBalance() - withdrawAmount;

                        if (clientDAO.updateBalance(client.getAccountNumber(), newBalance)) {
                            client.setBalance(newBalance);
                            transactionDAO.logTransaction(client.getAccountNumber(), withdrawAmount, "WITHDRAWAL");

                            System.out.println("💸 Dispensing cash... Please collect your money.");
                            System.out.println("✅ Withdrawal successful!");
                            System.out.println("💰 Remaining Balance: R" + client.getBalance());
                        } else {
                            System.out.println("❌ Transaction failed due to database error.");
                        }
                    } else {
                        System.out.println("❌ PIN verification failed. Withdrawal cancelled.");
                    }
                    break;

                case 4:
                    System.out.println("\n--- TRANSACTION HISTORY ---");
                    List<String> history = transactionDAO.getTransactions(client.getAccountNumber());
                    if (history.isEmpty()) {
                        System.out.println("No transactions found.");
                    } else {
                        for (String entry : history) {
                            System.out.println(entry);
                        }
                    }
                    break;

                case 5:
                    System.out.println("\n👋 Thank you for using Pilato Bank. Stay safe!");
                    exit = true;
                    break;

                default:
                    System.out.println("❌ Invalid choice. Please select a valid option (1-5).");
            }
        }
        input.close();
    }
}