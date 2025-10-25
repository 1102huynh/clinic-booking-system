package huynh.tdt.clinicbookingsystem.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utility class to generate correct BCrypt password hashes
 * Run this as a simple Java program to get the exact hashes
 */
public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String[] passwords = {"admin123", "password123"};

        System.out.println("=".repeat(80));
        System.out.println("BCrypt Password Hash Generator");
        System.out.println("=".repeat(80));
        System.out.println();

        for (String password : passwords) {
            String hash = encoder.encode(password);
            boolean matches = encoder.matches(password, hash);

            System.out.println("Password: " + password);
            System.out.println("Hash: " + hash);
            System.out.println("Verification: " + matches);
            System.out.println();
        }

        System.out.println("=".repeat(80));
        System.out.println("SQL UPDATE STATEMENTS:");
        System.out.println("=".repeat(80));
        System.out.println();

        String admin123 = encoder.encode("admin123");
        String password123 = encoder.encode("password123");

        System.out.println("UPDATE clinic_db.users SET password = '" + admin123 + "' WHERE username = 'admin';");
        System.out.println("UPDATE clinic_db.users SET password = '" + password123 + "' WHERE username = 'drsmith';");
        System.out.println("UPDATE clinic_db.users SET password = '" + password123 + "' WHERE username = 'patient01';");
        System.out.println("UPDATE clinic_db.users SET enabled = TRUE;");
        System.out.println();
    }
}

