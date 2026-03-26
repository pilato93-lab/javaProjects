import java.util.Scanner;

public class MorningPractice {

    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);

        System.out.println("what is your name?");
        String name = input.nextLine();

        System.out.println("how old are you?");
        int age = input.nextInt();

        System.out.println("Where do you live");
        String location = input.nextLine();

        int NextYearAge = age + 1; 

        System.out.println("Hello " + name + ".");
        System.out.println("Your are " + age + "years old.");
        System.out.println("Next year you will be" + NextYearAge + " years old.");
        System.out.println("you live in " + location);


        input.close();
        

    }
}