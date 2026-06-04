package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/pilato_bank_db";
    private static final String USER = "root";
    private static final String PASSWORD = "PilatoNgubz_01";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("🚀 [DATABASE] Connection to pilato_bank_db established successfully!");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ [ERROR] MySQL JDBC Driver not found. Check pom.xml dependency.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ [ERROR] Connection failed! Double check your URL, Username, or Password.");
            e.printStackTrace();
        }
        return connection;
    }

    // Quick test run
    public static void main(String[] args) {
        getConnection();
    }
}