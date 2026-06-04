package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClientDAO {

    public boolean registerClient(Client client) {
        String sql = "INSERT INTO clients (account_number, first_name, last_name, hashed_pin, balance) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, client.getAccountNumber());
            pstmt.setString(2, client.getFirstName());
            pstmt.setString(3, client.getLastName());
            pstmt.setString(4, client.getPin());
            pstmt.setDouble(5, client.getBalance());

            // Execute the insert statement
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("❌ [DATABASE ERROR] Registration failed!");
            e.printStackTrace();
            return false;
        }
    }

        public Client getClientByAccountNumber (String accountNumber){
            String sql = "SELECT * FROM clients WHERE account_number = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // 1. Put the account number the user typed into the SQL command
                pstmt.setString(1, accountNumber);

                // 2. Run the search query and store the results table
                try (java.sql.ResultSet rs = pstmt.executeQuery()) {

                    // 3. If the database found a matching row...
                    if (rs.next()) {
                        // Create and return a complete Client object using the data from the columns
                        return new Client(
                                rs.getString("account_number"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("hashed_pin"),
                                rs.getDouble("balance")
                        );
                    }
                }

            } catch (java.sql.SQLException e) {
                System.out.println("❌ [DATABASE ERROR] Failed to search for account: " + accountNumber);
                e.printStackTrace();
            }

            // If no match was found, or an error happened, return nothing
            return null;
        }
    }