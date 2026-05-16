import java.util.Scanner;

public class Hello {

  public static void main(String[] args) {
    
  Scanner input = new Scanner(System.in);

  System.out.println("Hello User");

  System.out.println("What is your name?");
  String name = input.nextLine();

  System.out.println("How old are you?");
  int age = input.nextInt();

  System.out.println("Welcome to java");

  System.out.println("your name is " + name);

  System.out.println("you are " + age + " years old");

  input.close();

  }

}