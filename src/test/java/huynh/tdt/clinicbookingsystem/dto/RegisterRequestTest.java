package huynh.tdt.clinicbookingsystem.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterRequestTest {

    private RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
    }

    @Test
    void testSetAndGetUsername() {
        registerRequest.setUsername("johndoe");
        assertEquals("johndoe", registerRequest.getUsername());
    }

    @Test
    void testSetAndGetPassword() {
        registerRequest.setPassword("password123");
        assertEquals("password123", registerRequest.getPassword());
    }

    @Test
    void testSetAndGetConfirmPassword() {
        registerRequest.setConfirmPassword("password123");
        assertEquals("password123", registerRequest.getConfirmPassword());
    }

    @Test
    void testPasswordsMatch() {
        registerRequest.setPassword("password123");
        registerRequest.setConfirmPassword("password123");
        assertEquals(registerRequest.getPassword(), registerRequest.getConfirmPassword());
    }

    @Test
    void testPasswordsMismatch() {
        registerRequest.setPassword("password123");
        registerRequest.setConfirmPassword("different");
        assertNotEquals(registerRequest.getPassword(), registerRequest.getConfirmPassword());
    }

    @Test
    void testSetAndGetFullname() {
        registerRequest.setFullname("John Doe");
        assertEquals("John Doe", registerRequest.getFullname());
    }

    @Test
    void testSetAndGetEmail() {
        registerRequest.setEmail("john@example.com");
        assertEquals("john@example.com", registerRequest.getEmail());
    }

    @Test
    void testCompleteRequest() {
        registerRequest.setUsername("testuser");
        registerRequest.setPassword("pass123");
        registerRequest.setConfirmPassword("pass123");
        registerRequest.setFullname("Test User");
        registerRequest.setEmail("test@example.com");

        assertEquals("testuser", registerRequest.getUsername());
        assertEquals("pass123", registerRequest.getPassword());
        assertEquals("pass123", registerRequest.getConfirmPassword());
        assertEquals("Test User", registerRequest.getFullname());
        assertEquals("test@example.com", registerRequest.getEmail());
    }
}
