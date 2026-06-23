package org.example;

import java.sql.*;
import java.util.Optional;

public class AccountJdbcRepository implements AccountRepository {

    @Override
    public void save(Account account) {
        // ADDED account_type
        String sql = "INSERT INTO clients (account_number, first_name, balance, hashed_pin, account_type) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, account.getAccountNumber());
            stmt.setString(2, account.getName());
            stmt.setDouble(3, account.getBalance());
            stmt.setString(4, account.getPin());
            stmt.setString(5, account.getAccountType()); // Mapping the account type
            stmt.executeUpdate();
            System.out.println("[DATABASE] Account successfully saved with type: " + account.getAccountType());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM clients WHERE account_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Added account_type retrieval to the constructor
                    Account account = new Account(
                            rs.getString("account_number"),
                            rs.getString("first_name"),
                            rs.getDouble("balance"),
                            rs.getString("hashed_pin"),
                            rs.getString("account_type")
                    );
                    return Optional.of(account);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void updateBalance(String accountNumber, double newBalance) {
        String sql = "UPDATE clients SET balance = ? WHERE account_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, newBalance);
            stmt.setString(2, accountNumber);
            stmt.executeUpdate();
            System.out.println("[DATABASE] Balance successfully updated in cloud storage!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}