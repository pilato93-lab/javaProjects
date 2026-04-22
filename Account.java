import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Account {
    private String name;
    private String id;
    private String pin; 
    private double balance;
    private double savingsBalance;
    private String accountTier = "Bronze";
    private double dailyLimit = 2000.0;
    private String mainHistoryFile;
    private String savingsHistoryFile;

    public Account(String name, String id, String pin, double balance, double savingsBalance) {
        this.name = name;
        this.id = id;
        this.pin = pin;
        this.balance = balance;
        this.savingsBalance = savingsBalance;
        this.mainHistoryFile = id + "_main_history.txt";
        this.savingsHistoryFile = id + "_savings_history.txt";
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            logTransaction(mainHistoryFile, "DEPOSIT", amount, balance);
            saveBalances(); 
            System.out.println("Deposited R" + amount);
        }
    }

    public void withdraw(double amount) {
        if (amount > dailyLimit) {
            System.out.println("Transaction Denied: You have reached your " + accountTier + " daily limit of R" + dailyLimit);
        } else if (amount > balance) {
            System.out.println("Transaction Denied: Insufficient funds.");
        } else if (amount <= 0) {
            System.out.println("Transaction Denied: Amount must be greater than zero.");
        } else {
            balance -= amount;
            logTransaction(mainHistoryFile, "WITHDRAWAL", amount, balance);
            saveBalances(); 
            System.out.println("Success! Please take your R" + amount);
        }
    }

    public void transferToSavings(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            savingsBalance += amount;
            logTransaction(mainHistoryFile, "TRANSFER TO SAVINGS", amount, balance);
            logTransaction(savingsHistoryFile, "RECEIVED FROM MAIN", amount, savingsBalance);
            saveBalances(); 
            System.out.println("Transfer Successful!");
        } else {
            System.out.println("Transfer Denied: Check balance.");
        }
    }

    public void transferFromSavings(double amount) {
        if (amount > 0 && amount <= savingsBalance) {
            savingsBalance -= amount;
            balance += amount;
            logTransaction(savingsHistoryFile, "TRANSFER TO MAIN", amount, savingsBalance);
            logTransaction(mainHistoryFile, "RECEIVED FROM SAVINGS", amount, balance);
            saveBalances();
            System.out.println("Transfer to Main successful!");
        } else {
            System.out.println("Transfer Denied: Insufficient savings funds.");
        }
    }

    public void saveBalances() {
        try {
            File inputFile = new File("users.txt");
            File tempFile = new File("temp.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            PrintWriter writer = new PrintWriter(new FileWriter(tempFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] d = line.split(",");
                if (d[0].equals(this.id)) {
                    writer.println(id + "," + pin + "," + name + "," + balance + "," + savingsBalance);
                } else {
                    writer.println(line);
                }
            }
            writer.close();
            reader.close();
            inputFile.delete();
            tempFile.renameTo(inputFile);
        } catch (Exception e) { }
    }

    private void logTransaction(String fileName, String type, double amount, double currentBal) {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName, true))) {
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            out.println(time + " | " + type + " | R" + amount + " | Balance: R" + currentBal);
        } catch (Exception e) { }
    }

    public void viewMainHistory() {
        System.out.println("\n--- MAIN HISTORY: " + id + " ---");
        displayFile(mainHistoryFile);
    }

    public void viewSavingsHistory() {
        System.out.println("\n--- SAVINGS HISTORY: " + id + " ---");
        displayFile(savingsHistoryFile);
    }

    private void displayFile(String fileName) {
        try (Scanner s = new Scanner(new File(fileName))) {
            while (s.hasNextLine()) System.out.println(s.nextLine());
        } catch (Exception e) { System.out.println("No history found for this account."); }
    }

    public String getDetails() {
        return "\nHOLDER: " + name + "\nID: " + id + "\nMAIN: R" + balance + "\nSAVINGS: R" + savingsBalance;
    }

    public double getSavingsBalance() {
        return savingsBalance;
    }

    public void printSlip() {
        System.out.println("\n------- SLIP -------");
        System.out.println("HOLDER: " + name);
        System.out.println("ACCOUNT: " + id);
        System.out.println("MAIN BALANCE: R" + balance);
        System.out.println("SAVINGS BALANCE: R" + savingsBalance);
        System.out.println("--------------------");
    }
}