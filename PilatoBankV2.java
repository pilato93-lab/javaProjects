import java.util.Scanner;
import java.io.*;
import java.security.MessageDigest;

public class PilatoBankV2 {
    private static final String BRANCH = "7443";
    private static final String REGISTRY = "users.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== PILATO BANK V2.0 ===");
        System.out.println("1. Login\n2. Register");
        System.out.print("Choice: ");
        String choice = scanner.nextLine();

        Account userAcc = null;

        if (choice.equals("1")) {
            System.out.print("Account ID: "); String id = scanner.nextLine();
            System.out.print("PIN: "); String pin = scanner.nextLine();
            userAcc = handleLogin(id, pin);
        } else {
            userAcc = handleRegister(scanner);
        }

        if (userAcc != null) {
            runMenu(scanner, userAcc);
        }
        System.out.println("Session Ended. Goodnight, Pilato!");
        scanner.close();
    }

    // Professional PIN Hashing Method
    private static String hashPIN(String pin) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(pin.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
            
        } catch (Exception e) {
            System.out.println("Security Error.");
            return null;
        }
    }

    // Professional Name Capitalization
    private static String capitalizeName(String name) {
        if (name == null || name.isEmpty()) return name;
        String[] words = name.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                sb.append(Character.toUpperCase(word.charAt(0)))
                  .append(word.substring(1).toLowerCase())
                  .append(" ");
            }
        }
        return sb.toString().trim();
    }

    private static Account handleLogin(String id, String pin) {
        try (Scanner reg = new Scanner(new File(REGISTRY))) {
            while (reg.hasNextLine()) {
                String[] d = reg.nextLine().split(",");
                // Verify by hashing the input PIN and comparing to the stored hash
                if (d[0].equals(id) && d[1].equals(hashPIN(pin))) {
                    double mainB = d.length > 3 ? Double.parseDouble(d[3]) : 0.0;
                    double saveB = d.length > 4 ? Double.parseDouble(d[4]) : 0.0;
                    return new Account(d[2], id, pin, mainB, saveB);
                }
            }
        } catch (Exception e) { }
        System.out.println("Invalid Credentials.");
        return null;
    }

    private static Account handleRegister(Scanner sc) {
        System.out.print("Full Name: "); 
        String rawName = sc.nextLine();
        String name = capitalizeName(rawName); // Capitalizes the name automatically
        
        System.out.print("Create 4-digit PIN: "); 
        String pin = sc.nextLine();
        String id = generateID();
        
        // Secure the PIN before saving
        String securedPin = hashPIN(pin); 
        
        try (PrintWriter out = new PrintWriter(new FileWriter(REGISTRY, true))) {
            out.println(id + "," + securedPin + "," + name + ",0.0,0.0");
        } catch (Exception e) { }
        System.out.println("Registered! Your ID: " + id);
        return new Account(name, id, pin, 0.0, 0.0);
    }

    private static void runMenu(Scanner sc, Account acc) {
        while (true) {
            System.out.println("\n=== SELECT ACCOUNT ===");
            System.out.println("1. Main Account");
            System.out.println("2. Savings Account");
            System.out.println("3. Exit");
            System.out.print("Selection: ");
            String accountChoice = sc.nextLine();

            if (accountChoice.equals("1")) {
                runMainAccountMenu(sc, acc);
            } else if (accountChoice.equals("2")) {
                runSavingsAccountMenu(sc, acc);
            } else if (accountChoice.equals("3")) {
                System.out.println("Logging securely out...");
                break;
            }
        }
    }

    private static void runMainAccountMenu(Scanner sc, Account acc) {
        while (true) {
            System.out.println("\n--- MAIN ACCOUNT ---");
            System.out.println("1. Balance\n2. Deposit\n3. Withdraw\n4. History\n5. Slip\n6. Transfer to Savings\n7. Back to Account Selection");
            System.out.print("Selection: ");
            String c = sc.nextLine();
            
            if (c.equals("1")) System.out.println(acc.getDetails());
            else if (c.equals("2")) { System.out.print("R"); acc.deposit(Double.parseDouble(sc.nextLine())); }
            else if (c.equals("3")) { System.out.print("R"); acc.withdraw(Double.parseDouble(sc.nextLine())); }
            else if (c.equals("4")) acc.viewMainHistory();
            else if (c.equals("5")) acc.printSlip();
            else if (c.equals("6")) { 
                System.out.print("Amount to transfer to Savings: R"); 
                acc.transferToSavings(Double.parseDouble(sc.nextLine())); 
            }
            else if (c.equals("7")) break;
        }
    }

    private static void runSavingsAccountMenu(Scanner sc, Account acc) {
        while (true) {
            System.out.println("\n--- SAVINGS ACCOUNT ---");
            System.out.println("1. View Balance\n2. View Transactions\n3. Transfer to Main\n4. Back to Account Selection");
            System.out.print("Selection: ");
            String c = sc.nextLine();
            
            if (c.equals("1")) System.out.println("\nSAVINGS BALANCE: R" + acc.getSavingsBalance());
            else if (c.equals("2")) acc.viewSavingsHistory();
            else if (c.equals("3")) {
                System.out.print("Amount to transfer to Main: R");
                acc.transferFromSavings(Double.parseDouble(sc.nextLine()));
            }
            else if (c.equals("4")) break;

        }
    }

    private static String generateID() {
        int last = 0;
        try (Scanner s = new Scanner(new File("last_id.txt"))) { if (s.hasNextInt()) last = s.nextInt(); } catch (Exception e) { }
        try (PrintWriter o = new PrintWriter(new FileWriter("last_id.txt"))) { o.print(last + 1); } catch (Exception e) { }
        return BRANCH + String.format("%02d", last + 1);
        
    }
}