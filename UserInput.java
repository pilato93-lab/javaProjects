import java.util.Scanner;

public class UserInput {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("What is your name?");
        String name = input.nextLine();

        System.out.println("How old are you?");
        int age = input.nextInt();

        int currentYear = 2026;
        int birthYear = currentYear - age; 

        System.out.println("That means you were born in " + birthYear + "!");

        input.nextLine(); //<_____
        System.out.println("where do you live?");
        String live = input.nextLine();

        System.out.println("which coding language do you use?");
        String use = input.nextLine();

        System.out.println("how long is your course?");
        int period = input.nextInt();

        int currentyear =2026;
        int gradyear =currentyear + (period / 12);
        

        System.out.println("Hello " + name + "!");
        System.out.println("You are " + age + " years old.");
        System.out.println("You live in " + live + ".");
        System.out.println("You use " + use + " as your programming language.");
        System.out.println("your course is " + period + " months long");

        System.out.println();
        
        System.out.println("Good luck " + name + ".");
        System.out.println("You will graduate in the year "  +gradyear + "!");

    
        input.close();

    }

}