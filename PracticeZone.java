import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;

public class PilatoBankTest {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
            boolean isRunning = true;
            boolean isAuthenticated = false;

            int choice = 0;
            double balance = 0.0;
            String firstName = "";
            String surname = "";
            int dob = 0;
            String address = "";

            String currentFirstName = "";
            String currentAccountId = "";

            int failedAttempts = 0;
            boolean isLockedOut = false;
            String isRegisterPin = "";

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

while (isRunning) {
   try {
    System.out.println("MAIN MENU");
    System.out.println("");
    System.out.println("1. Login");
    System.out.println("2. Register");
    System.out.println("3. Exit");
        choice = input.nextInt();
            input.nextLine();

    switch (choice) {
        case 1:
            System.out.println("WELCOME TO PILATO BANK");
            break;

        case 2:
            System.out.println("Enter your firstNames");
            String firstNames = input.nextLine();
                input.nextLine();
    
            System.out.println("Enter your surname");
            String surnameString = input.nextLine();

            System.out.println("Enter your Pin");
            int Pin = input.nextInt();

            System.out.println("account opened! your Pin is " + Pin);
            break;
        
            case 3:
                System.out.println("Goodbye! Thanks for using Pilato Bank");
                isRunning = false;
            break;
    }

   } catch (InputMismatchException e) {
        System.out.println("Invalid choice");
        input.nextLine();
   }

}

    }
}