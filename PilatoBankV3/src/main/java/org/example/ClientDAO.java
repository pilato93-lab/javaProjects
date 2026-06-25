package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    // Fetching client from the database using their account number
    public Client getClientByAccountNumber(String accountNumber) {
        String query = "SELECT * FROM clients WHERE account_number = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, accountNumber);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Client client = new Client("", "", "", "", 0.0);
                    client.setAccountNumber(rs.getString("account_number"));
                    client.setFirstName(rs.getString("first_name"));
                    client.setLastName(rs.getString("last_name"));
                    client.setPin(rs.getString("hashed_pin"));
                    client.setBalance(rs.getDouble("balance"));
                    client.setAccountType(rs.getString("account_type"));
                    return client;
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Database Error: Could not retrieve client details.");
            e.printStackTrace();
        }
        return null;
    }

    // Updating client's balance in the database
    public boolean updateBalance(String accountNumber, double amount) {
        String query = "UPDATE clients SET balance = balance + ? WHERE account_number = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDouble(1, amount);
            pstmt.setString(2, accountNumber);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("❌ Database Error: Could not update balance.");
            e.printStackTrace();
            return false;
        }
    }

    // Process withdrawal securely
    public boolean withdrawFunds(String accountNumber, double amount) {
        String query = "UPDATE clients SET balance = balance - ? WHERE account_number = ? AND balance >= ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDouble(1, amount);
            pstmt.setString(2, accountNumber);
            pstmt.setDouble(3, amount);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("❌ Database Error: Could not process withdrawal.");
            e.printStackTrace();
            return false;
        }
    }

    // Fetching account balance from the database
    public double getBalance(String accountNumber) {
        String query = "SELECT balance FROM clients WHERE account_number = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, accountNumber);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Database Error: Could not retrieve balance.");
            e.printStackTrace();
        }
        return -1.0;
    }

    // Fetching transaction history
    public List<Transaction> getTransactionHistory(String accountNumber) {
        List<Transaction> history = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE account_number = ? ORDER BY timestamp DESC LIMIT 10";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, accountNumber);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Transaction t = new Transaction();
                    // FIXED: Changed "id" to "transaction_id" to match your database schema
                    t.setId(rs.getInt("transaction_id"));
                    t.setAccountNumber(rs.getString("account_number"));
                    t.setTransactionType(rs.getString("transaction_type"));
                    t.setAmount(rs.getDouble("amount"));
                    t.setTimestamp(rs.getTimestamp("timestamp"));

                    history.add(t);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Database Error: Could not retrieve history.");
            e.printStackTrace();
        }
        return history;
    }
}