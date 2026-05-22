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

        int failedAttempts = 0; 
        boolean isLockedOut = false; 

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            File filebalance = new File("balance.txt");
            if (filebalance.exists()) {
                Scanner fileScanner = new Scanner(filebalance);
                if (fileScanner.hasNextLine()) {
                    balance = Double.parseDouble(fileScanner.nextLine());
                }
                fileScanner.close();
            }
        } catch (Exception e) {
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
            
            if (isLockedOut) {
                System.out.println("TERMINAL LOCKED. Please contact Pilato Bank administration.");
                System.exit(0); 
            }

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
                        failedAttempts = 0; 
                    } else {
                        failedAttempts++; 
                        int remaining = 3 - failedAttempts; 
                        
                        if (failedAttempts >= 3) {
                            System.out.println("CRITICAL SECURITY ALERT: Too many incorrect attempts. Account is locked.");
                            isLockedOut = true; 
                            System.exit(0); 
                        } else {
                            System.out.println("incorrect pin! You have " + remaining + " attempts remaining.");
                        }
                    }
                    break;

                case 2:
                    System.out.println("Your balance is R" + balance);
                    break;

                case 3:
                    System.out.println("Enter deposit amount");
                    double depositAmount = input.nextDouble();
                    input.nextLine();
                    
                    balance = balance + depositAmount;

                    try {
                        FileWriter writer = new FileWriter("balance.txt");
                        writer.write(String.valueOf(balance));
                        writer.close();
                    } catch (Exception e) {
                        System.out.println("CRITICAL ERROR: Could not save balance to file!");
                    }

                    LocalDateTime depositTime = LocalDateTime.now();
                    try {
                        FileWriter historyWriter = new FileWriter("history.txt", true);
                        historyWriter.write("[" + depositTime.format(timeFormat) + "] Deposit: +R" + depositAmount + " | Balance: R" + balance + "\n");
                        historyWriter.close();
                    } catch (Exception e) {
                        System.out.println("CRITICAL ERROR: Could not update transaction history!");
                    }
                    
                    System.out.println("\n==================================");
                    System.out.println("          PILATO BANK ATM         ");
                    System.out.println("         TRANSACTION RECEIPT      ");
                    System.out.println("==================================");
                    System.out.println("Date/Time: " + depositTime.format(timeFormat));
                    System.out.println("Type:      DEPOSIT");
                    System.out.println("Amount:    +R" + depositAmount);
                    System.out.println("Balance:   R" + balance);
                    System.out.println("==================================");
                    System.out.println("    Thank you for banking with us! ");
                    System.out.println("==================================\n");
                    break;
                    
                case 4:
                    System.out.println("\n--- WITHDRAWAL ---");
                    System.out.println("1. R100");
                    System.out.println("2. R200");
                    System.out.println("3. R500");
                    System.out.println("4. R1000");
                    System.out.println("5. Enter Own Amount");
                    System.out.println("6. Cancel");
                    System.out.print("Select an option: ");
                    
                    int withdrawChoice = input.nextInt();
                    double withdrawAmount = 0;

                    if (withdrawChoice == 1) withdrawAmount = 100;
                    else if (withdrawChoice == 2) withdrawAmount = 200;
                    else if (withdrawChoice == 3) withdrawAmount = 500;
                    else if (withdrawChoice == 4) withdrawAmount = 1000;
                    else if (withdrawChoice == 5) {
                        System.out.print("Enter amount to withdraw: R");
                        withdrawAmount = input.nextDouble();
                        input.nextLine(); 
                    } else if (withdrawChoice == 6) {
                        System.out.println("Transaction cancelled.");
                        break; 
                    } else {
                        System.out.println("Invalid selection.");
                        break; 
                    }

                    if (withdrawAmount > 0) {
                        if (withdrawAmount > balance) {
                            System.out.println("Insufficient funds! Your current balance is R" + balance);
                        } else {
                            balance -= withdrawAmount;
                            
                            try {
                                FileWriter writer = new FileWriter("balance.txt");
                                writer.write(String.valueOf(balance)); 
                                writer.close();
                            } catch (IOException e) {
                                System.out.println("CRITICAL ERROR: Could not save balance to file!");
                            }
                                                    
                            LocalDateTime withdrawTime = LocalDateTime.now();
                            try {
                                FileWriter historyWriter = new FileWriter("history.txt", true); 
                                historyWriter.write("[" + withdrawTime.format(timeFormat) + "] Withdraw: -R" + withdrawAmount + " | Balance: R" + balance + "\n");
                                historyWriter.close();
                            } catch (IOException e) {
                                System.out.println("CRITICAL ERROR: Could not update transaction history!");
                            }

                            System.out.println("\n==================================");
                            System.out.println("          PILATO BANK ATM         ");
                            System.out.println("         TRANSACTION RECEIPT      ");
                            System.out.println("==================================");
                            System.out.println("Date/Time: " + withdrawTime.format(timeFormat));
                            System.out.println("Type:      WITHDRAWAL");
                            System.out.println("Amount:    -R" + withdrawAmount);
                            System.out.println("Balance:   R" + balance);
                            System.out.println("==================================");
                            System.out.println("    Thank you for banking with us! ");
                            System.out.println("==================================\n");
                        }
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