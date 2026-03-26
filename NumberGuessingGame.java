import java.util.Random;
import java.util.Scanner; // Added semicolon

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int secretNumber = random.nextInt(100) + 1;

        System.out.println("Guess the number between 1 and 100");
        System.out.print("enter your Guess "); // Capitalized 'S' in System

        int UserGuess = scanner.nextInt();

        if (UserGuess > secretNumber) {
            System.out.println("Too high");
        } else if (UserGuess < secretNumber) {
            System.out.println("Too low");
        } else {
            System.out.println("you guessed the number correctly");
        }

        scanner.close();
    }
}
