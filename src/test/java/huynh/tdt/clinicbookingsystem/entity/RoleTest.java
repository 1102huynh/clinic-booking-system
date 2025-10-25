package huynh.tdt.clinicbookingsystem.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
    }

    @Test
    void testSetAndGetId() {
        role.setId(1L);
        assertEquals(1L, role.getId());
    }

    @Test
    void testSetAndGetName() {
        role.setName("ROLE_ADMIN");
        assertEquals("ROLE_ADMIN", role.getName());
    }

    @Test
    void testRoleAdmin() {
        role.setId(1L);
        role.setName("ROLE_ADMIN");

        assertEquals(1L, role.getId());
        assertEquals("ROLE_ADMIN", role.getName());
    }

    @Test
    void testRoleDoctor() {
        role.setId(2L);
        role.setName("ROLE_DOCTOR");

        assertEquals(2L, role.getId());
        assertEquals("ROLE_DOCTOR", role.getName());
    }

    @Test
    void testRolePatient() {
        role.setId(3L);
        role.setName("ROLE_PATIENT");

        assertEquals(3L, role.getId());
        assertEquals("ROLE_PATIENT", role.getName());
    }

    @Test
    void testRoleNameUpdate() {
        role.setName("ROLE_USER");
        assertEquals("ROLE_USER", role.getName());

        role.setName("ROLE_ADMIN");
        assertEquals("ROLE_ADMIN", role.getName());
    }

    @Test
    void testRoleNotNull() {
        assertNotNull(role);
        role.setId(1L);
        role.setName("ROLE_TEST");
        assertNotNull(role.getId());
        assertNotNull(role.getName());
    }
}
