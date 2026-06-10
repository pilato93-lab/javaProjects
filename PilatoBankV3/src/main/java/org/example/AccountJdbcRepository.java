package org.example;

import java.sql.*;
import java.util.Optional;

public class AccountJdbcRepository implements AccountRepository {

    @Override
    public void save(Account account) {
        String sql = "INSERT INTO clients (account_number, first_name, balance, hashed_pin) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, account.getAccountNumber());
            stmt.setString(2, account.getName());
            stmt.setDouble(3, account.getBalance());
            stmt.setString(4, account.getPin());
            stmt.executeUpdate();
            System.out.println("[DATABASE] Account successfully saved via Repository!");

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
                    Account account = new Account(
                            rs.getString("account_number"),
                            rs.getString("first_name"),
                            rs.getDouble("balance"),
                            rs.getString("hashed_pin")
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