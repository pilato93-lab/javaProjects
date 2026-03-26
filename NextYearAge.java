import java.util.Scanner;

public class NextYearAge {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        
        System.out.println("What is your name?");
                String name = scanner.nextLine();

                System.out.println("how old are you?");
                int age = scanner.nextInt();

                int NextYearAge = age + 1;

                System.out.println(" ");

                System.out.println("Enter your Password");
                String password = scanner.next();

                System.out.println("hello " + name);
                System.out.println("next year will be " + NextYearAge);

                System.out.println(" ");

                if ("PilatoNgubz_01".equals(password)) {
                    System.out.println("Access granted");

                } else {

                    System.out.println("Incorrect Password");
                }

                if (name.equals("Pilato")) {
                    System.out.println("welcome " + name +"!");
                    
                } else {
                    System.out.println("unauthorized user");  
                }

                if (age < 18) {
                    System.out.println("you are a minor.");   
                } 
                
                else if ( age < 35) {

                    System.out.println("you are a young adult.");
                    
                }
                
                else if ( age > 90 ) {
                    System.out.println("you are a senior citizen.");
                    
                } else {
                    System.out.println("you are an adult.");
                }
                scanner.close();

    }
}