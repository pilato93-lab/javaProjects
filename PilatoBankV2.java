import java.util.Scanner;
import java.io.*;

public class PilatoBankV2 {
    private static final String BRANCH = "7443";
    private static final String REGISTRY = "users.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== PILATO BANK V2.0 ===");
        System.out.println("1. Login\n2. Register");
        System.out.print("Choice: ");
        String choice = scanner.nextLine();

        Account userAcc = choice.equals("1") ? handleLogin(scanner) : handleRegister(scanner);

        if (userAcc != null) {
            runMenu(scanner, userAcc);
        }
        System.out.println("Session Ended. Goodnight, Pilato!");
        scanner.close();
    }

    private static Account handleLogin(Scanner sc) {
        System.out.print("Account ID: "); String id = sc.nextLine();
        System.out.print("PIN: "); String pin = sc.nextLine();
        try (Scanner reg = new Scanner(new File(REGISTRY))) {
            while (reg.hasNextLine()) {
                String[] d = reg.nextLine().split(",");
                if (d[0].equals(id) && d[1].equals(pin)) return new Account(d[2], id, "Main");
            }
        } catch (Exception e) { }
        System.out.println("Invalid Credentials.");
        return null;
    }

    private static Account handleRegister(Scanner sc) {
        System.out.print("Full Name: "); String name = sc.nextLine();
        System.out.print("Create 4-digit PIN: "); String pin = sc.nextLine();
        String id = generateID();
        try (PrintWriter out = new PrintWriter(new FileWriter(REGISTRY, true))) {
            out.println(id + "," + pin + "," + name);
        } catch (Exception e) { }
        System.out.println("Registered! Your ID: " + id);
        return new Account(name, id, "Main");
    }

    private static void runMenu(Scanner sc, Account acc) {
        while (true) {
            System.out.println("\n1. Balance\n2. Deposit\n3. Withdraw\n4. History\n5. Slip\n6. Exit");
            System.out.print("Selection: ");
            String c = sc.nextLine();
            if (c.equals("1")) System.out.println(acc.getDetails());
            else if (c.equals("2")) { System.out.print("R"); acc.deposit(Double.parseDouble(sc.nextLine())); }
            else if (c.equals("3")) { System.out.print("R"); acc.withdraw(Double.parseDouble(sc.nextLine())); }
            else if (c.equals("4")) acc.viewHistory();
            else if (c.equals("5")) acc.printSlip();
            else if (c.equals("6")) break;
        }
    }

    private static String generateID() {
        int last = 0;
        try (Scanner s = new Scanner(new File("last_id.txt"))) { if (s.hasNextInt()) last = s.nextInt(); } catch (Exception e) { }
        try (PrintWriter o = new PrintWriter(new FileWriter("last_id.txt"))) { o.print(last + 1); } catch (Exception e) { }
        return BRANCH + String.format("%02d", last + 1);
    }
}