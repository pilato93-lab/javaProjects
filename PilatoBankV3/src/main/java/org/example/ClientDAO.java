package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDAO {

   //Fetching client from the database using their account number
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
                    return client;
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Database Error: Could not retrieve client details.");
            e.printStackTrace();
        }
        return null;
    }

    //Updating client's balance in the database
    public boolean updateBalance(String accountNumber, double newBalance) {
        String query = "UPDATE clients SET balance = ? WHERE account_number = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, accountNumber);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
           System.out.println("❌ Database Error: Could not update balance.");
          e.printStackTrace();
            return false;
        }
    }
 }