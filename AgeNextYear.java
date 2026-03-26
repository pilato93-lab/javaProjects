import java.util.Scanner;

public class AgeNextYear {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("What is your name?");
        String name = scanner.nextLine();

        System.out.println("Which year did you start your coding journey?");
        int year = scanner.nextInt();

        System.out.println("How many years is your course?");
        int duration = scanner.nextInt();

        int gradyear = year + duration;

        System.out.println("Hello " + name);
        System.out.println("Welcome to Java programming");
        System.out.println("You are expected to graduate in " + gradyear + ".");
        System.out.println("");
        System.out.println("Good luck " + name);

          System.out.println("");
              System.out.println("");
               System.out.println("");
                System.out.println("");

        if (gradyear > 2030) {
            System.out.println("It's a long journey, stay strong");
        } else {
            System.out.println("You're almost there, keep pushing");
           

        }

        scanner.close();
    }
}