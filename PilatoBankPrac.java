import java.util.Scanner;
    public class PilatoBankPrac {
    
        public static void main(String[] args) {
            Scanner input = new Scanner(System.in);
            boolean isRunning = true;
            double balance = 0;
            int choice = 0;

     System.out.println("PILATO BANK PRAC");

while (isRunning) {
    System.out.println("1. login");
    System.out.println("2. balance");
    System.out.println("3. deposit");
    System.out.println("4. withdraw");
    System.out.println("5. Exit");
        input.hasNextInt();

if (input.hasNextInt()) {
    choice = input.nextInt();
        input.nextLine();

switch (choice) {
    case 1:
     System.out.println("Welcome to PILATO BANK PRAC");
        break;

    case 2:
     System.out.println("your balance is R" + balance);
        break;

    case 3:
    System.out.println("Enter deposit amount");
        double depositAmount = input.nextDouble();
        balance = balance + depositAmount;
            input.nextLine();
    System.out.println("Success. Your new balance is R" + balance);
        break;

case 4:
    System.out.println("Enter amount to withdraw R");
    double withdrawAmount = input.nextDouble();

    if (withdrawAmount > balance) {
        System.out.println("Insufficient funds! Your balance isR " + balance);
    } 
    else {

        balance = balance - withdrawAmount;
        System.out.println("Success! Please take your cash.");
        System.out.println("Your new balance is R " + balance);
    }
    
    input.nextLine();
        break;

    case 5:
    System.out.println("Goodbye");
        input.nextLine();
            isRunning = false;
            break;
}

} else {
    System.out.println("invalid choice");
}

}
input.close();
    }
}