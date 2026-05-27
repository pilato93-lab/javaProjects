import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;

public class PilatoBankPrac2 {
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        boolean isRunning = true;
        boolean isAuthenticated = false;
        
        int choice = 0;
        
        String firstName = "";
        String surname = "";
        String dob = "";
        String address = "";
        int registeredPin = 0;
        
        String currentAccountId = "";
        String currentFirstName = "";
        double balance = 0.0;
        int failedAttempts = 0; 
        boolean isLockedOut = false; 

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        while (!isAuthenticated) {
            if (isLockedOut) {
                System.out.println("\nTERMINAL LOCKED. Please contact Pilato Bank administration.");
                System.exit(0); 
            }

            System.out.println("\n==================================");
            System.out.println("      WELCOME TO PILATO BANK      ");
            System.out.println("==================================");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            
            int authChoice = 0;
            try {
                authChoice = input.nextInt();
                input.nextLine(); 
            } catch (InputMismatchException e) {
                System.out.println("\n[Error] Invalid input. Defaulting to Welcome menu.");
                input.nextLine();
                continue;
            }

            if (authChoice == 2) {
                System.out.print("\nEnter First Name: ");
                firstName = input.nextLine(); 
                System.out.print("Enter Surname: ");
                surname = input.nextLine(); 
                System.out.print("Enter Date of Birth (YYYY MM DD): ");
                dob = input.nextLine(); 
                System.out.print("Enter Residential Address: ");
                address = input.nextLine(); 
                
                boolean validPin = false;
                while (!validPin) {
                    System.out.print("Create a 4-digit PIN: ");
                    try {
                        registeredPin = input.nextInt();
                        input.nextLine(); 
                        validPin = true;
                    } catch (InputMismatchException e) {
                        System.out.println("\n[Error] Invalid PIN format. Please enter numbers only.\n");
                        input.nextLine();
                    }
                }

                int accountCounter = 1;
                try {
                    File lastIdFile = new File("last_id.txt");
                    if (lastIdFile.exists()) {
                        Scanner idScanner = new Scanner(lastIdFile);
                        if (idScanner.hasNextInt()) {
                            accountCounter = idScanner.nextInt() + 1;
                        }
                        idScanner.close();
                    }
                } catch (Exception e) {
                    System.out.println("[System Error] Could not read ID tracker.");
                }
                
                String newAccountId = "7443" + String.format("%03d", accountCounter);
                
                try {
                    FileWriter fw = new FileWriter("last_id.txt");
                    fw.write(String.valueOf(accountCounter));
                    fw.close();
                } catch (Exception e) {}

                try {
                    FileWriter fw = new FileWriter("users.txt", true);
                    fw.write(newAccountId + "," + registeredPin + "," + firstName + "," + surname + "\n");
                    fw.close();
                } catch (Exception e) {
                    System.out.println("[System Error] Could not save user data.");
                }
                
                System.out.println("\n==================================");
                System.out.println("      REGISTRATION SUCCESSFUL     ");
                System.out.println("==================================");
                System.out.println("Account Holder: " + firstName + " " + surname);
                System.out.println("Your Account ID: " + newAccountId);
                System.out.println("Please keep your Account ID and PIN safe.");
                System.out.println("==================================\n");

            } else if (authChoice == 1) {
                System.out.print("\nEnter your Account ID: ");
                String loginId = input.nextLine();
                
                System.out.print("Enter your PIN: ");
                int loginPin = 0;
                try {
                    loginPin = input.nextInt();
                    input.nextLine(); 
                } catch (InputMismatchException e) {
                    System.out.println("\n[Error] PIN must be numeric.");
                    input.nextLine();
                    continue;
                }
                
                boolean foundUser = false;
                try {
                    File usersFile = new File("users.txt");
                    if (usersFile.exists()) {
                        Scanner s = new Scanner(usersFile);
                        while(s.hasNextLine()) {
                            String line = s.nextLine();
                            String[] data = line.split(",");
                            if (data.length >= 4) {
                                if (data[0].equals(loginId) && Integer.parseInt(data[1]) == loginPin) {
                                    foundUser = true;
                                    currentAccountId = data[0];
                                    currentFirstName = data[2];
                                    break;
                                }
                            }
                        }
                        s.close();
                    }
                } catch (Exception e) {
                    System.out.println("[System Error] User database not found.");
                }

                if (foundUser) {
                    System.out.println("\nAccess granted. Welcome back, " + currentFirstName + "!");
                    isAuthenticated = true;
                    failedAttempts = 0; 
                    
                    try {
                        File filebalance = new File(currentAccountId + "_balance.txt");
                        if (filebalance.exists()) {
                            Scanner fileScanner = new Scanner(filebalance);
                            if (fileScanner.hasNextLine()) {
                                balance = Double.parseDouble(fileScanner.nextLine());
                            }
                            fileScanner.close();
                        } else {
                            balance = 0.0; 
                        }
                    } catch (Exception e) {}

                } else {
                    failedAttempts++; 
                    int remaining = 3 - failedAttempts; 
                    
                    if (failedAttempts >= 3) {
                        System.out.println("\nCRITICAL SECURITY ALERT: Too many incorrect attempts. Account is locked.");
                        isLockedOut = true; 
                    } else {
                        System.out.println("\nIncorrect Account ID or PIN! You have " + remaining + " attempts remaining.");
                    }
                }
            } else if (authChoice == 3) {
                System.out.println("\nGoodbye.");
                System.exit(0);
            } else {
                System.out.println("\n[Error] Invalid choice. Please select 1, 2, or 3.");
            }
        }

        while (isRunning) {
            
            System.out.println("\n==================================");
            System.out.println("             MAIN MENU            ");
            System.out.println("       Account: " + currentAccountId);
            System.out.println("==================================");
            System.out.println("1. Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transaction History");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            
            try {
                choice = input.nextInt();
                input.nextLine(); 
            } catch (InputMismatchException e) {
                System.out.println("\n[Error] Invalid input. Please enter a number between 1 and 5.");
                input.nextLine();
                continue;
            }

            switch (choice) {
                
                case 1:
                    System.out.println("\nYour balance is R" + balance);
                    break;

                case 2:
                    System.out.print("\nEnter deposit amount: R");
                    double depositAmount = 0;
                    try {
                        depositAmount = input.nextDouble();
                        input.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("\n[Error] Invalid amount. Deposit cancelled.");
                        input.nextLine();
                        continue;
                    }
                    
                    balance = balance + depositAmount;

                    try {
                        FileWriter writer = new FileWriter(currentAccountId + "_balance.txt");
                        writer.write(String.valueOf(balance));
                        writer.close();
                    } catch (Exception e) {
                        System.out.println("CRITICAL ERROR: Could not save balance to file!");
                    }

                    LocalDateTime depositTime = LocalDateTime.now();
                    try {
                        FileWriter historyWriter = new FileWriter(currentAccountId + "_history.txt", true);
                        historyWriter.write("[" + depositTime.format(timeFormat) + "] Deposit: +R" + depositAmount + " | Balance: R" + balance + "\n");
                        historyWriter.close();
                    } catch (Exception e) {
                        System.out.println("CRITICAL ERROR: Could not update transaction history!");
                    }
                    
                    System.out.println("\n==================================");
                    System.out.println("          PILATO BANK ATM         ");
                    System.out.println("         TRANSACTION RECEIPT      ");
                    System.out.println("==================================");
                    System.out.println("Account:   " + currentAccountId);
                    System.out.println("Date/Time: " + depositTime.format(timeFormat));
                    System.out.println("Type:      DEPOSIT");
                    System.out.println("Amount:    +R" + depositAmount);
                    System.out.println("Balance:   R" + balance);
                    System.out.println("==================================");
                    System.out.println("    Thank you for banking with us! ");
                    System.out.println("==================================\n");
                    break;
                    
                case 3:
                    boolean validWithdrawal = false;
                    while (!validWithdrawal) {
                        System.out.println("\n--- WITHDRAWAL ---");
                        System.out.println("1. R100");
                        System.out.println("2. R200");
                        System.out.println("3. R500");
                        System.out.println("4. R1000");
                        System.out.println("5. Enter Own Amount");
                        System.out.println("6. Cancel");
                        System.out.print("Select an option: ");
                        
                        int withdrawChoice = 0;
                        try {
                            withdrawChoice = input.nextInt();
                            input.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("\n[Error] Invalid selection. Please choose a menu option between 1 and 6.");
                            input.nextLine(); 
                            continue; 
                        }
                        
                        double withdrawAmount = 0;

                        if (withdrawChoice == 1) withdrawAmount = 100;
                        else if (withdrawChoice == 2) withdrawAmount = 200;
                        else if (withdrawChoice == 3) withdrawAmount = 500;
                        else if (withdrawChoice == 4) withdrawAmount = 1000;
                        else if (withdrawChoice == 5) {
                            System.out.print("\nEnter amount to withdraw: R");
                            try {
                                withdrawAmount = input.nextDouble();
                                input.nextLine(); 
                            } catch (InputMismatchException e) {
                                System.out.println("\n[Error] Invalid amount entered.");
                                input.nextLine();
                                continue; 
                            }
                        } else if (withdrawChoice == 6) {
                            System.out.println("\nTransaction cancelled.");
                            validWithdrawal = true; 
                            break; 
                        } else {
                            System.out.println("\n[Error] Invalid selection. Please choose a menu option between 1 and 6.");
                            continue; 
                        }

                        if (withdrawAmount > 0) {
                            if (withdrawAmount > balance) {
                                System.out.println("\nInsufficient funds! Your current balance is R" + balance);
                                validWithdrawal = true; 
                            } else {
                                balance -= withdrawAmount;
                                
                                try {
                                    FileWriter writer = new FileWriter(currentAccountId + "_balance.txt");
                                    writer.write(String.valueOf(balance)); 
                                    writer.close();
                                } catch (IOException e) {
                                    System.out.println("CRITICAL ERROR: Could not save balance to file!");
                                }
                                                                                
                                LocalDateTime withdrawTime = LocalDateTime.now();
                                try {
                                    FileWriter historyWriter = new FileWriter(currentAccountId + "_history.txt", true); 
                                    historyWriter.write("[" + withdrawTime.format(timeFormat) + "] Withdraw: -R" + withdrawAmount + " | Balance: R" + balance + "\n");
                                    historyWriter.close();
                                } catch (IOException e) {
                                    System.out.println("CRITICAL ERROR: Could not update transaction history!");
                                }

                                System.out.println("\n==================================");
                                System.out.println("          PILATO BANK ATM         ");
                                System.out.println("         TRANSACTION RECEIPT      ");
                                System.out.println("==================================");
                                System.out.println("Account:   " + currentAccountId);
                                System.out.println("Date/Time: " + withdrawTime.format(timeFormat));
                                System.out.println("Type:      WITHDRAWAL");
                                System.out.println("Amount:    -R" + withdrawAmount);
                                System.out.println("Balance:   R" + balance);
                                System.out.println("==================================");
                                System.out.println("    Thank you for banking with us! ");
                                System.out.println("==================================\n");
                                
                                validWithdrawal = true; 
                            }
                        }
                    }
                    break;

                case 4:
                    System.out.println("\n--- TRANSACTION HISTORY ---");
                    try {
                        File historyFile = new File(currentAccountId + "_history.txt");
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

                case 5:
                    try {
                        FileWriter writer = new FileWriter(currentAccountId + "_balance.txt");
                        writer.write(String.valueOf(balance));
                        writer.close();
                    } catch (IOException e) {
                        System.out.println("[System: Error saving balance]");
                    }
                                        
                    System.out.println("\nGoodbye. Logging out...");
                    isRunning = false;
                    
                    isAuthenticated = false;
                    currentAccountId = "";
                    currentFirstName = "";
                    balance = 0.0;
                    break;
                    
                default:
                    System.out.println("\n[Error] Invalid choice. Please select an option from 1 to 5.");
                    break;
            }   
        }
    }
}