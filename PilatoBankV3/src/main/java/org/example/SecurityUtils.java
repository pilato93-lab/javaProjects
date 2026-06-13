package org.example;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class SecurityUtils {

    public static String hashPin(String rawPin) {
        try {

            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] encodedHash = digest.digest(rawPin.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {

                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (Exception e) {
            throw new RuntimeException("Error: Hashing algorithm failed.", e);
        }
    }
}