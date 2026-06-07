package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDAO {

    // The driving instructions
    public void logTransaction(Transaction transaction) {

        // Security Checkpoints route
        String sql = "INSERT INTO transactions (account_number, transaction_type, amount) VALUES (?, ?, ?)";

        // garage door to MySQL
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Matching to the checkpoints
            pstmt.setString(1, transaction.getAccountNumber());
            pstmt.setString(2, transaction.getTransactionType());
            pstmt.setDouble(3, transaction.getAmount());

            // Saving to database
            pstmt.executeUpdate();
            System.out.println("📝 [LEDGER] " + transaction.getTransactionType() + " of R" + transaction.getAmount() + " securely logged.");

        } catch (SQLException e) {
            System.out.println("❌ Error logging transaction: " + e.getMessage());
        }
    }
}