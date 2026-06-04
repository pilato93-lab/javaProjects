package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
       Scanner input = new Scanner(System.in);
       String accountNumber = "";

       System.out.println("============================");
       System.out.println("Welcome to PILATO BANK V3");
       System.out.println("============================");
       System.out.println("Please enter your account number");
       accountNumber = input.nextLine();
       ClientDAO clientDAO = new ClientDAO();
        Client client = clientDAO.getClientByAccountNumber(accountNumber);

        if (client == null) {
            System.out.println("❌ Account not found");
        } else {
            System.out.println("Welcome back, " + client.getFirstName() + "!");
        }
    }
}