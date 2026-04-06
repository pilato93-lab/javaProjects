public class Account {
    private String ownerName;
    private String accountNumber;
    private String type;
    private double balance;
    private String lastTransaction = "No transactions yet."; // The 'Slip' memory

    public Account(String ownerName, String accountNumber, String type) {
        this.ownerName = ownerName;
        this.accountNumber = accountNumber;
        this.type = type;
        this.balance = 0.0;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            
            this.lastTransaction = "TYPE: Deposit | AMOUNT: R" + amount + " | NEW BALANCE: R" + this.balance;
            System.out.println("[SUCCESS] Deposit recorded.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
            
            this.lastTransaction = "TYPE: Withdrawal | AMOUNT: R" + amount + " | NEW BALANCE: R" + this.balance;
            System.out.println("[SUCCESS] Withdrawal recorded.");
        } else {
            System.out.println("[DENIED] Transaction failed.");
        }
    }

    // printing the  slip
    public void printSlip() {
        System.out.println("\n------- TRANSACTION SLIP -------");
        System.out.println("ACCOUNT: " + this.accountNumber);
        System.out.println("HOLDER: " + this.ownerName);
        System.out.println("DETAILS: " + this.lastTransaction);
        System.out.println("--------------------------------");
    }

    public String getDetails() {
        return "Account: " + accountNumber + " | Type: " + type + " | Balance: R" + balance;
    }
}