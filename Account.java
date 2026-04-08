import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Account {
    private String holderName;
    private String accountID;
    private String type;
    private double balance;

    public Account(String holderName, String accountID, String type) {
        this.holderName = holderName;
        this.accountID = accountID;
        this.type = type;
        this.balance = 0.0;
        loadBalanceFromHistory(); // Restores balance from file on startup
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            recordTransaction("DEPOSIT", amount);
            System.out.println("R" + amount + " deposited successfully.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
            recordTransaction("WITHDRAWAL", amount);
            System.out.println("R" + amount + " withdrawn successfully.");
        } else {
            System.out.println("[ERROR] Insufficient funds.");
        }
    }

    public void viewHistory() {
        String fileName = this.accountID + "_history.txt";
        File file = new File(fileName);
        System.out.println("\n--- TRANSACTION HISTORY: " + accountID + " ---");
        if (!file.exists()) {
            System.out.println("No transactions found.");
            return;
        }
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) System.out.println(sc.nextLine());
        } catch (Exception e) {
            System.out.println("[ERROR] Could not read history.");
        }
    }

    public String getDetails() {
        return "ID: " + accountID + " | Type: " + type + " | Balance: R" + balance;
    }

    public void printSlip() {
        System.out.println("\n------- FINAL SLIP -------");
        System.out.println("HOLDER: " + holderName);
        System.out.println("ACCOUNT: " + accountID);
        System.out.println("FINAL BALANCE: R" + balance);
        System.out.println("--------------------------");
    }

    private void recordTransaction(String type, double amt) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        try (PrintWriter out = new PrintWriter(new FileWriter(this.accountID + "_history.txt", true))) {
            out.println(time + " | " + type + " | R" + amt + " | Balance: R" + this.balance);
        } catch (IOException e) { }
    }

    private void loadBalanceFromHistory() {
        File file = new File(this.accountID + "_history.txt");
        if (!file.exists()) return;
        try (Scanner sc = new Scanner(file)) {
            String lastLine = "";
            while (sc.hasNextLine()) lastLine = sc.nextLine();
            if (lastLine.contains("Balance: R")) {
                this.balance = Double.parseDouble(lastLine.substring(lastLine.lastIndexOf("R") + 1).trim());
            }
        } catch (Exception e) { }
    }
}