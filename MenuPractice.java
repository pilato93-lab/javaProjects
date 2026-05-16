import java.util.Scanner;
public class MenuPractice {

    public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    
    int choice = 0; 
    
    boolean isRunning = true;
    
    while (isRunning) {

    
            System.out.println("Enter your choice:");

if (input.hasNextInt()) {
    choice = input.nextInt();

    switch (choice) {
        case 1:
            System.out.println("Balance chosen");
            break;
        case 2:
            System.out.println("Deposit chosen");
            break;
        case 3:
            System.out.println("Goodbye");
            isRunning = false;
            break;
        default:
            System.out.println("Invalid option. Please choose 1-3.");
    }
} else {
    System.out.println("Error: Please enter a number, not text!");
    input.next(); 
}
            
    }
input.close();
     
}
    
    }