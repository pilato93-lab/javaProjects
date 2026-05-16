import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PilatoBankPrac2 {
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        boolean isRunning = true;
        int choice = 0;
        int correctPin = 1234;
        String registeredName = "";
        int registeredPin = 0;
        boolean isRegistered = false;
        double balance = 0.0;

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            File balanceFile = new File("balance.txt");
            if (balanceFile.exists()) {
                Scanner fileScanner = new Scanner(balanceFile);
                if (fileScanner.hasNextLine()) {
                    balance = Double.parseDouble(fileScanner.nextLine());
                }
                fileScanner.close();
            }
        } catch (Exception e) {
            // Silently defaults to 0.0 if file is missing or unreadable
        }

        System.out.println("PILATO BANK PRAC 2");
        System.out.println("1. Login");
        System.out.println("2. Register");
        int initialChoice = input.nextInt();
        input.nextLine(); 

        if (initialChoice == 2) {
            System.out.print("Enter your Full Name: ");
            registeredName = input.nextLine();
            System.out.print("Create a 4-digit PIN: ");
            registeredPin = input.nextInt();
            input.nextLine(); 
            isRegistered = true;
        }

        while (isRunning) {
            System.out.println("\nEnter your choice:");
            System.out.println("1. Login");
            System.out.println("2. Balance");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Transaction History");
            System.out.println("6. Exit");
            
            choice = input.nextInt();
            input.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.println("Enter your pin:");
                    int Pin = input.nextInt();
                    input.nextLine(); 
                    if (Pin == correctPin) {
                        System.out.println("access granted");
                    } else {
                        System.out.println("incorrect pin!");
                    }
                    break;

                case 2:
                    System.out.println("Your balance is R" + balance);
                    break;

                case 3:
                    System.out.println("Enter deposit amount:");
                    double depositAmount = input.nextDouble();
                    input.nextLine(); 
                    balance = balance + depositAmount;
                    
                    try {
                        FileWriter writer = new FileWriter("balance.txt");
                        writer.write(String.valueOf(balance)); 
                        writer.close();
                    } catch (IOException e) {
                        System.out.println("CRITICAL ERROR: Could not save balance to file!");
                    }
                    
                    try {
                        FileWriter historyWriter = new FileWriter("history.txt", true); 
                        LocalDateTime now = LocalDateTime.now();
                        historyWriter.write("[" + now.format(timeFormat) + "] Deposit: +R" + depositAmount + " | Balance: R" + balance + "\n");
                        historyWriter.close();
                    } catch (IOException e) {
                        System.out.println("CRITICAL ERROR: Could not update transaction history!");
                    }
                    
                    System.out.println("Success! Your new balance is R" + balance);
                    break;

                case 4:
                    System.out.println("Enter withdrawal amount:");
                    double withdrawAmount = input.nextDouble();
                    input.nextLine();
                    
                    if (withdrawAmount > balance) {
                        System.out.println("Insufficient funds! Your current balance is R" + balance);
                    } else if (withdrawAmount <= 0) {
                        System.out.println("Invalid amount. Please enter a value greater than zero.");
                    } else {
                        balance = balance - withdrawAmount;
                        try {
                            FileWriter writer = new FileWriter("balance.txt");
                            writer.write(String.valueOf(balance)); 
                            writer.close();
                        } catch (IOException e) {
                            System.out.println("CRITICAL ERROR: Could not save balance to file!");
                        }
                        
                        try {
                            FileWriter historyWriter = new FileWriter("history.txt", true); 
                            LocalDateTime now = LocalDateTime.now();
                            historyWriter.write("[" + now.format(timeFormat) + "] Withdraw: -R" + withdrawAmount + " | Balance: R" + balance + "\n");
                            historyWriter.close();
                        } catch (IOException e) {
                            System.out.println("CRITICAL ERROR: Could not update transaction history!");
                        }
                        
                        System.out.println("Success! Your new balance is R" + balance);
                    }
                    break;

                case 5:
                    System.out.println("\n--- TRANSACTION HISTORY ---");
                    try {
                        File historyFile = new File("history.txt");
                        if (historyFile.exists()) {
                            Scanner historyScanner = new Scanner(historyFile);
                            while (historyScanner.hasNextLine()) {
                                System.out.println(historyScanner.nextLine());
                            }
                            historyScanner.close();
                        } else {
                            System.out.println("No transactions yet.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error reading transaction history.");
                    }
                    System.out.println("---------------------------\n");
                    break;

                case 6:
                    try {
                        FileWriter writer = new FileWriter("balance.txt");
                        writer.write(String.valueOf(balance));
                        writer.close();
                    } catch (IOException e) {
                        System.out.println("[System: Error saving balance]");
                    }
                    
                    System.out.println("Goodbye");
                    isRunning = false;
                    break;
            }   
        }
        input.close();
    }
}