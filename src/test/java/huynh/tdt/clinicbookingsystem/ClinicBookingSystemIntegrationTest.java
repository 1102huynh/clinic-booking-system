package huynh.tdt.clinicbookingsystem;

import huynh.tdt.clinicbookingsystem.dto.RegisterRequest;
import huynh.tdt.clinicbookingsystem.entity.User;
import huynh.tdt.clinicbookingsystem.repository.UserRepository;
import huynh.tdt.clinicbookingsystem.service.RegistrationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ClinicBookingSystemIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testUserRegistrationFlow() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setUsername("integrationuser");
        request.setPassword("password123");
        request.setConfirmPassword("password123");
        request.setFullname("Integration User");
        request.setEmail("integration@example.com");

        // Act
        boolean registered = registrationService.registerUser(request);

        // Assert
        assertTrue(registered);
        Optional<User> savedUser = userRepository.findByUsername("integrationuser");
        assertTrue(savedUser.isPresent());
        assertEquals("PATIENT", savedUser.get().getRole());
        assertTrue(savedUser.get().isEnabled());
    }

    @Test
    void testPasswordEncryptionOnRegistration() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setUsername("encryptiontest");
        request.setPassword("plainpassword");
        request.setConfirmPassword("plainpassword");

        // Act
        registrationService.registerUser(request);
        Optional<User> user = userRepository.findByUsername("encryptiontest");

        // Assert
        assertTrue(user.isPresent());
        assertNotEquals("plainpassword", user.get().getPassword());
        assertTrue(passwordEncoder.matches("plainpassword", user.get().getPassword()));
    }

    @Test
    void testDuplicateUsernamePreventsDuplicate() {
        // Arrange
        RegisterRequest request1 = new RegisterRequest();
        request1.setUsername("duplicate");
        request1.setPassword("password123");
        request1.setConfirmPassword("password123");

        RegisterRequest request2 = new RegisterRequest();
        request2.setUsername("duplicate");
        request2.setPassword("password456");
        request2.setConfirmPassword("password456");

        // Act
        boolean first = registrationService.registerUser(request1);
        boolean second = registrationService.registerUser(request2);

        // Assert
        assertTrue(first);
        assertFalse(second);
    }

    @Test
    void testPasswordMismatchPreventRegistration() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setUsername("mismatchuser");
        request.setPassword("password123");
        request.setConfirmPassword("password456");

        // Act
        boolean registered = registrationService.registerUser(request);

        // Assert
        assertFalse(registered);
        Optional<User> user = userRepository.findByUsername("mismatchuser");
        assertFalse(user.isPresent());
    }

    @Test
    void testMultipleUsersCanBeRegistered() {
        // Arrange
        RegisterRequest user1 = new RegisterRequest();
        user1.setUsername("multiuser1");
        user1.setPassword("pass1");
        user1.setConfirmPassword("pass1");

        RegisterRequest user2 = new RegisterRequest();
        user2.setUsername("multiuser2");
        user2.setPassword("pass2");
        user2.setConfirmPassword("pass2");

        // Act
        boolean reg1 = registrationService.registerUser(user1);
        boolean reg2 = registrationService.registerUser(user2);

        // Assert
        assertTrue(reg1);
        assertTrue(reg2);
    }

    @Test
    void testUserPersistence() {
        // Arrange
        User user = new User();
        user.setUsername("persistencetest");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setEmail("persist@example.com");
        user.setFullName("Persistence Test");
        user.setRole("PATIENT");
        user.setEnabled(true);

        // Act
        User saved = userRepository.save(user);
        Optional<User> retrieved = userRepository.findById(saved.getId());

        // Assert
        assertTrue(retrieved.isPresent());
        assertEquals("persistencetest", retrieved.get().getUsername());
    }
}
