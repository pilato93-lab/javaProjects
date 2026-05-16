import java.util.Scanner;
    public class ScannerTest {
    
        public static void main(String[] args) {
            
            Scanner input = new Scanner(System.in);

            System.out.println("Enter your age:");
                int age = input.nextInt();

            System.out.println("Thank you. age recorded");

            System.out.println(" ");

            System.out.println("Enter your full name;");
                String name = input.next();

                System.out.println("Results: " + name + " is " + age + "years old.");

                input.close();
            
        }


    }