package huynh.tdt.clinicbookingsystem.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testUserDefaultValues() {
        assertTrue(user.isEnabled());
    }

    @Test
    void testSetAndGetId() {
        user.setId(1L);
        assertEquals(1L, user.getId());
    }

    @Test
    void testSetAndGetUsername() {
        user.setUsername("testuser");
        assertEquals("testuser", user.getUsername());
    }

    @Test
    void testSetAndGetPassword() {
        String encodedPassword = "$2a$10$encoded";
        user.setPassword(encodedPassword);
        assertEquals(encodedPassword, user.getPassword());
    }

    @Test
    void testSetAndGetFullName() {
        user.setFullName("John Doe");
        assertEquals("John Doe", user.getFullName());
    }

    @Test
    void testSetAndGetEmail() {
        user.setEmail("john@example.com");
        assertEquals("john@example.com", user.getEmail());
    }

    @Test
    void testSetAndGetRole() {
        user.setRole("ADMIN");
        assertEquals("ADMIN", user.getRole());
    }

    @Test
    void testSetAndIsEnabled() {
        user.setEnabled(false);
        assertFalse(user.isEnabled());

        user.setEnabled(true);
        assertTrue(user.isEnabled());
    }

    @Test
    void testSetAndGetRoles() {
        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_ADMIN");

        java.util.Set<Role> roles = new java.util.HashSet<>();
        roles.add(role);

        user.setRoles(roles);
        assertNotNull(user.getRoles());
        assertEquals(1, user.getRoles().size());
    }

    @Test
    void testUserCompleteSetup() {
        user.setId(1L);
        user.setUsername("admin");
        user.setPassword("encodedpass");
        user.setFullName("System Admin");
        user.setEmail("admin@clinic.com");
        user.setRole("ADMIN");
        user.setEnabled(true);

        assertEquals(1L, user.getId());
        assertEquals("admin", user.getUsername());
        assertEquals("System Admin", user.getFullName());
        assertEquals("admin@clinic.com", user.getEmail());
        assertEquals("ADMIN", user.getRole());
        assertTrue(user.isEnabled());
    }
}
