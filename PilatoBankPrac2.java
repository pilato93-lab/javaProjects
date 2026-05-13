import java.util.Scanner;
    public class PilatoBankPrac2 {
    
        public static void main(String[] args) {
            Scanner input = new Scanner(System.in);
                boolean isRunning = true;
                double balance =0.0;
                int choice = 0;
                int correctPin = 1234;

    System.out.println("PILATO BANK PRAC 2");

while (isRunning) {
    System.out.println("Enter your choice");
    System.out.println(" ");
    System.out.println("1. Login");
    System.out.println("2. Balance");
    System.out.println("3. Deposit");
    System.out.println("4. Withdraw");
    System.out.println("5. Exit");
            choice = input.nextInt();
                input.nextLine();


switch (choice) {
    case 1:
        System.out.println("Enter your pin");
        int Pin = input.nextInt();
if (Pin == correctPin) {
    System.out.println("access granted");
            input.nextLine();

} else {
    System.out.println("incorrect pin!");
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
        System.out.println("Your new balance is R" + balance);
        break;

    case 4:
        System.out.println("Enter withdrawal amount");
        double WithdrawAmount = input.nextDouble();
            input.nextLine();
        balance = balance - WithdrawAmount;

if (WithdrawAmount > balance) {
        System.out.println("Insufficient funds");
    
} else {
        System.out.println("Success. your new balance is R" + balance);
}
        break;

    case 5:
        System.out.println("Goodbye");
        isRunning = false;
        break;
   
}   
}
    input.close();
        }
    } 