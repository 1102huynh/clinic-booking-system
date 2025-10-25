package huynh.tdt.clinicbookingsystem.service;

import huynh.tdt.clinicbookingsystem.dto.RegisterRequest;
import huynh.tdt.clinicbookingsystem.entity.User;
import huynh.tdt.clinicbookingsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrationService registrationService;

    private RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setUsername("testuser");
        registerRequest.setPassword("password123");
        registerRequest.setConfirmPassword("password123");
    }

    @Test
    void testRegisterUserSuccess() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("$2a$10$encoded");
        when(userRepository.save(any(User.class))).thenReturn(new User());

        // Act
        boolean result = registrationService.registerUser(registerRequest);

        // Assert
        assertTrue(result);
        verify(userRepository).findByUsername("testuser");
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testRegisterUserDuplicateUsername() {
        // Arrange
        User existingUser = new User();
        existingUser.setUsername("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(existingUser));

        // Act
        boolean result = registrationService.registerUser(registerRequest);

        // Assert
        assertFalse(result);
        verify(userRepository).findByUsername("testuser");
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void testRegisterUserPasswordMismatch() {
        // Arrange
        registerRequest.setConfirmPassword("different");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        // Act
        boolean result = registrationService.registerUser(registerRequest);

        // Assert
        assertFalse(result);
        verify(userRepository).findByUsername("testuser");
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void testRegisterUserSetsDefaultRole() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("$2a$10$encoded");

        User capturedUser = new User();
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User u = invocation.getArgument(0);
            capturedUser.setRole(u.getRole());
            capturedUser.setEnabled(u.isEnabled());
            return u;
        });

        // Act
        registrationService.registerUser(registerRequest);

        // Assert
        assertEquals("PATIENT", capturedUser.getRole());
        assertTrue(capturedUser.isEnabled());
    }

    @Test
    void testRegisterUserEncryptsPassword() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("$2a$10$encrypted");
        when(userRepository.save(any(User.class))).thenReturn(new User());

        // Act
        registrationService.registerUser(registerRequest);

        // Assert
        verify(passwordEncoder).encode("password123");
    }
}
