import java.util.Scanner;

public class MidDayPrac {
    
    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);

        String correctPassword = "Admin";

        System.out.println("Insert Password:");
        String inputPassword = input.nextLine();

        if (inputPassword.equals(correctPassword)) {
            System.out.println("Access Granted");
        } else {
            System.out.println("Access Denied");
        }

        System.out.println("What is your name?");
        String name = input.nextLine();

        System.out.println("How old are you?");
        int age = input.nextInt();

        System.out.println("Hello " + name + "!");
        System.out.println("You are " + age + " years old");

        System.out.println("");

        if (age < 13) {
            System.out.println("You are a child");
        } else if (age <= 35) {
            System.out.println("You are a young adult");
        } else {
            System.out.println("You are an adult");
        }

        input.close();
    }
}