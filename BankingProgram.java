import java.util.Scanner;

public class BankingProgram {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // --- SECTION 1: SECURITY GATE ---
        int storedPin = 7443; 
        int enteredPin;
        boolean accessGranted = false;

        System.out.println("=== PILATO BANK SECURE LOGIN ===");
        
        while (!accessGranted) {
            System.out.print("Please enter your 4-digit PIN to begin: ");
            enteredPin = scanner.nextInt();

            if (enteredPin == storedPin) {
                accessGranted = true;
                System.out.println("\nAccess Granted. Welcome back!");
            } else {
                System.out.println("Incorrect PIN. Please try again.");
            }
        }

        // --- SECTION 2: THE BANKING PROGRAM (Unlocked) ---
        double balance = 0.00;
        int choice;

        do {
            System.out.println(" ");
            System.out.println("BANKING PROGRAM");
            System.out.println(" ");
            System.out.println("1. Show Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.println(" ");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Your current balance is: R" + balance);
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: R");
                    double depositAmount = scanner.nextDouble();
                    balance += depositAmount;
                    System.out.println("Successfully deposited R" + depositAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to withdraw: R");
                    double withdrawAmount = scanner.nextDouble();
                    if (withdrawAmount > balance) {
                        System.out.println("Insufficient funds! Your balance is R" + balance);
                    } else {
                        balance -= withdrawAmount;
                        System.out.println("Successfully withdrew R" + withdrawAmount);
                    }
                    break;
                case 4:
                    System.out.println("Thank you for using Pilato Banking. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please pick 1, 2, 3, or 4.");
            }

        } while (choice != 4);

        scanner.close();
    }
}