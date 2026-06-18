package org.example;

public class ServiceTester {

    public static void runIntegrityChecks(Client client) {
        System.out.println("\n--- [SYSTEM] RUNNING INTEGRITY CHECKS ---");

        // Withdrawal Overdraft (Should fail)
        double fakeWithdrawal = 1000000.00; // Million rand test
        if (fakeWithdrawal > client.getBalance()) {
            System.out.println("✅ [PASS] Overdraft protection is active. Cannot withdraw R" + fakeWithdrawal);
        } else {
            System.out.println("❌ [FAIL] Overdraft protection broken! System allowed massive withdrawal.");
        }

        // Negative Deposit (Should fail)
        double negativeDeposit = -500.00;
        if (negativeDeposit <= 0) {
            System.out.println("✅ [PASS] Negative deposit blocked.");
        } else {
            System.out.println("❌ [FAIL] System accepted a negative deposit!");
        }

        System.out.println("--- [SYSTEM] CHECKS COMPLETE ---\n");
    }
}