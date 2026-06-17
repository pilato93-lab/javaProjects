package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    // The driving instructions
    public void logTransaction(String accountNumber, double amount, String type) {

        // Security Checkpoints route
        String sql = "INSERT INTO transactions (account_number, transaction_type, amount) VALUES (?, ?, ?)";

        // garage door to MySQL
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Matching to the checkpoints
            pstmt.setString(1, accountNumber);
            pstmt.setString(2, type);
            pstmt.setDouble(3, amount);

            // Saving to database
            pstmt.executeUpdate();
            System.out.println("📝 [LEDGER] " + type + " of R" + amount + " securely logged.");

        } catch (SQLException e) {
            System.out.println("❌ Error logging transaction: " + e.getMessage());
        }
    }

    // New method to fetch history
    public List<String> getTransactions(String accountNumber) {
        List<String> history = new ArrayList<>();
        // Updated to ASC to show oldest transactions at the top
        String sql = "SELECT transaction_type, amount, timestamp FROM transactions WHERE account_number = ? ORDER BY timestamp ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String entry = String.format("[%s] %s: R%.2f",
                        rs.getTimestamp("timestamp"),
                        rs.getString("transaction_type"),
                        rs.getDouble("amount"));
                history.add(entry);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching history: " + e.getMessage());
        }
        return history;
    }
}