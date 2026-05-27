import java.util.Scanner;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BankingProgram {

    private static final String FILE_NAME = "transactions.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // --- SECTION 1: SECURITY GATE ---
        int storedPin = 7443;
        System.out.println("=== PILATO BANK SECURE LOGIN ===");
        System.out.print("Please enter your 4-digit PIN: ");
        
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input. PIN must be numbers only.");
            return;
        }

        if (scanner.nextInt() != storedPin) {
            System.out.println("Access Denied. Incorrect PIN.");
            return;
        }

        double balance = loadBalanceFromFile(); 
        
        System.out.println("\nAccess Granted. Welcome back!");
        System.out.println("Current Account Balance: R" + balance);

        int choice;
        do {
            System.out.println("\n--- MAIN BANKING MENU ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Funds");
            System.out.println("3. Withdraw Funds");
            System.out.println("4. View Transaction History");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Your current balance is: R" + balance);
                    break;
                case 2:
                    System.out.print("Enter deposit amount: R");
                    double dep = scanner.nextDouble();
                    if (dep > 0) {
                        balance += dep;
                        saveTransaction("DEPOSIT: R" + dep);
                        System.out.println("R" + dep + " has been added to your account.");
                    } else {
                        System.out.println("Invalid amount.");
                    }
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: R");
                    double with = scanner.nextDouble();
                    if (with > 0 && with <= balance) {
                        balance -= with;
                        saveTransaction("WITHDRAW: R" + with);
                        System.out.println("Please take your R" + with);
                    } else {
                        System.out.println("Transaction failed: Insufficient funds or invalid amount.");
                    }
                    break;
                case 4:
                    printHistory();
                    break;
                case 5:
                    System.out.println("Thank you for banking with Pilato Bank. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid selection. Try again.");
            }
        } while (choice != 5);

        scanner.close();
    }

    public static void saveTransaction(String text) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(formatter);

        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            out.println(timestamp + " - " + text);
        } catch (IOException e) {
            System.out.println("System Error: Could not log transaction.");
        }
    }

    public static double loadBalanceFromFile() {
        double calcBalance = 0;
        File file = new File(FILE_NAME);
        
        if (!file.exists()) return 0.0;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                
                if (line.contains("R")) {
                    // We find the VERY LAST 'R' to avoid confusion with dates
                    int lastRIndex = line.lastIndexOf("R");
                    String amountString = line.substring(lastRIndex + 1).trim();
                    double amount = Double.parseDouble(amountString);
                    
                    if (line.contains("DEPOSIT")) {
                        calcBalance += amount;
                    } else if (line.contains("WITHDRAW")) {
                        calcBalance -= amount;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Note: History file read error. Starting fresh.");
        }
        return calcBalance;
    }

    public static void printHistory() {
        System.out.println("\n--- PERMANENT TRANSACTION LOG ---");
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No records found.");
            return;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                System.out.println(fileScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        }
    }
}