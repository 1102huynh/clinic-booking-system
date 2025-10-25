package huynh.tdt.clinicbookingsystem.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utility to generate BCrypt password hashes
 * Run the main method to generate hashes for your passwords
 */
public class BCryptPasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String[] passwords = {
            "admin123",
            "password123",
            "testpass123"
        };

        System.out.println("===========================================");
        System.out.println("BCrypt Password Hash Generator");
        System.out.println("===========================================");

        for (String password : passwords) {
            String hash = encoder.encode(password);
            boolean matches = encoder.matches(password, hash);

            System.out.println("\nPassword: " + password);
            System.out.println("Hash:     " + hash);
            System.out.println("Verified: " + matches);
            System.out.println("SQL UPDATE:");
            System.out.println("UPDATE clinic_db.users SET password = '" + hash + "' WHERE password LIKE '%" + password.substring(0, 3) + "%' OR username = 'admin';");
        }

        System.out.println("\n===========================================");
        System.out.println("Copy the hashes above into your database");
        System.out.println("===========================================");
    }
}

