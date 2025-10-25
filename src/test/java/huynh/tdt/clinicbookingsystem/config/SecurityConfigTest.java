package huynh.tdt.clinicbookingsystem.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecurityConfigTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testPasswordEncoderNotNull() {
        assertNotNull(passwordEncoder);
    }

    @Test
    void testPasswordEncoderEncodeNotNull() {
        String encoded = passwordEncoder.encode("testpassword");
        assertNotNull(encoded);
        assertNotEquals("testpassword", encoded);
    }

    @Test
    void testPasswordEncoderMatches() {
        String plainPassword = "testpassword";
        String encoded = passwordEncoder.encode(plainPassword);

        assertTrue(passwordEncoder.matches(plainPassword, encoded));
    }

    @Test
    void testPasswordEncoderDoesNotMatch() {
        String plainPassword = "testpassword";
        String encoded = passwordEncoder.encode(plainPassword);

        assertFalse(passwordEncoder.matches("wrongpassword", encoded));
    }

    @Test
    void testPasswordEncoderWithSpecialCharacters() {
        String plainPassword = "P@ssw0rd!#$%";
        String encoded = passwordEncoder.encode(plainPassword);

        assertTrue(passwordEncoder.matches(plainPassword, encoded));
    }

    @Test
    void testPasswordEncoderAdmin123() {
        String plainPassword = "admin123";
        String encoded = passwordEncoder.encode(plainPassword);

        assertTrue(passwordEncoder.matches(plainPassword, encoded));
    }

    @Test
    void testPasswordEncoderPassword123() {
        String plainPassword = "password123";
        String encoded = passwordEncoder.encode(plainPassword);

        assertTrue(passwordEncoder.matches(plainPassword, encoded));
    }
}
