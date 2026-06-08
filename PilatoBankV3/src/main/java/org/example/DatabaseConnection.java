package org.example;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // 1. Create a Properties object to hold the keys
                Properties props = new Properties();

                // 2. Load the application.properties file from the resources folder
                try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("application.properties")) {
                    if (input == null) {
                        System.out.println("❌ [ERROR] Unable to find application.properties file.");
                        return null;
                    }
                    props.load(input);
                }

                // 3. Extract the details safely from the properties file
                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");

                // 4. Load the JDBC Driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // 5. Establish connection securely
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("⚡ [DATABASE] Connection to pilato_bank_db established successfully via properties!");

            } catch (ClassNotFoundException e) {
                System.out.println("❌ [ERROR] MySQL JDBC Driver not found. Check pom.xml dependency.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("❌ [ERROR] Connection failed! Double check your URL, Username, or Password.");
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("❌ [ERROR] Something went wrong loading configuration: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }
}