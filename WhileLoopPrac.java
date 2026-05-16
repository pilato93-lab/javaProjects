import java.util.Scanner;
    public class WhileLoopPrac {
    
        public static void main(String[] args) {
            Scanner input = new Scanner(System.in);
            boolean isRunning =true;
            int choice = 0;

while (isRunning) {
    System.out.println("1. Login");
    System.out.println("2. Exit");


    System.out.println("Enter your choice");

        if (input.hasNextInt()) {

            choice = input.nextInt();
                input.nextLine();
    

switch (choice) {
    case 1:
        System.out.println("Welcome to PILATO BANK");        
        break;

    case 2:
        System.out.println("Thank you. have a nice day");
          isRunning = false;
        break;

    case 3:
        System.out.println("invalid choice");

} 
    } else {
        System.out.println("invalid choice");
            input.nextLine();

    }
         }
input.close();         
        }
            }
            
