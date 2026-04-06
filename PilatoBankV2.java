import java.util.Scanner;

public class PilatoBankV2 {
    private static int lastAssignedNumber = 0; 
    private static final String BRANCH_CODE = "7443";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== PILATO BANK ONBOARDING ===");
        
        System.out.print("Enter name/s: ");
        String names = capitalizeAllWords(scanner.nextLine());
        System.out.print("Enter surname: ");
        String surname = capitalizeAllWords(scanner.nextLine());
        String fullName = names + " " + surname;

        // PIN Setup
        String userPin = "";
        do {
            System.out.print("Create 4-digit PIN: ");
            userPin = scanner.nextLine();
        } while (userPin.length() != 4 || !userPin.matches("\\d+"));

        // Account Creation
        Account myMainAccount = new Account(fullName, generateID(), "Main");
        Account mySavingsAccount = null;

        System.out.print("\nOpen a Savings account? (yes/no): ");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            mySavingsAccount = new Account(fullName, generateID(), "Savings");
        }

        // THE ATM MENU
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n--- ATM MAIN MENU ---");
            System.out.println("1. Check Balances");
            System.out.println("2. Deposit Money (Main)");
            System.out.println("3. Withdraw Money (Main)");
            System.out.println("4. Print Last Transaction Slip (Main)"); // NEW OPTION
            System.out.println("5. Exit");
            System.out.print("Selection: ");
            
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.println("\n" + myMainAccount.getDetails());
                if (mySavingsAccount != null) {
                    System.out.println(mySavingsAccount.getDetails());
                }
            } 
            else if (choice.equals("2")) {
                System.out.print("Enter deposit amount: R");
                try {
                    double amount = Double.parseDouble(scanner.nextLine());
                    myMainAccount.deposit(amount);
                } catch (Exception e) {
                    System.out.println("[ERROR] Invalid input. Use numbers only.");
                }
            } 
            else if (choice.equals("3")) {
                System.out.print("Enter withdrawal amount: R");
                try {
                    double amount = Double.parseDouble(scanner.nextLine());
                    myMainAccount.withdraw(amount);
                } catch (Exception e) {
                    System.out.println("[ERROR] Invalid input. Use numbers only.");
                }
            }
            else if (choice.equals("4")) {
                
                myMainAccount.printSlip();
            }
            else if (choice.equals("5")) {
                System.out.println("Closing session. Goodbye, Pilato!");
                isRunning = false;
            } else {
                System.out.println("[ERROR] Invalid selection. Please choose 1-5.");
            }
        }
        scanner.close();
    }

    public static String capitalizeAllWords(String str) {
        if (str == null || str.isEmpty()) return str;
        String[] words = str.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            sb.append(Character.toUpperCase(w.charAt(0))).append(w.substring(1).toLowerCase()).append(" ");
        }
        return sb.toString().trim();
    }

    public static String generateID() {
        lastAssignedNumber++;
        return BRANCH_CODE + String.format("%02d", lastAssignedNumber);
    }
}