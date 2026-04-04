 import java.util.Scanner;

public class Practice {

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        System.out.println("What is your name?");
        String name = scanner.nextLine();

        System.out.println("where do you live");
        String location = scanner.nextLine();

        System.out.println("how old are you?");
        int age = scanner.nextInt();


        System.out.println("hello " + name + "!");
        System.out.println("you are " + age + "years old");
        System.out.println("you live in " + location + ".");


        System.out.println(" ");
        System.out.println("your java course begins in");

    }
}