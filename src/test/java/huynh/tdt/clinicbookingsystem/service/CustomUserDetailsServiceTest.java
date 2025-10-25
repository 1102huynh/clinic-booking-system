package huynh.tdt.clinicbookingsystem.service;

import huynh.tdt.clinicbookingsystem.entity.Role;
import huynh.tdt.clinicbookingsystem.entity.User;
import huynh.tdt.clinicbookingsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setPassword("$2a$10$encoded");
        user.setFullName("System Admin");
        user.setEmail("admin@clinic.com");
        user.setRole("ADMIN");
        user.setEnabled(true);
    }

    @Test
    void testLoadUserByUsernameSuccess() {
        // Arrange
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("admin");

        // Assert
        assertNotNull(userDetails);
        assertEquals("admin", userDetails.getUsername());
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        // Arrange
        when(userRepository.findByUsername("notfound")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("notfound");
        });
    }

    @Test
    void testLoadUserByUsernameDisabledUser() {
        // Arrange
        user.setEnabled(false);
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("admin");
        });
    }

    @Test
    void testLoadUserByUsernameWithRole() {
        // Arrange
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("admin");

        // Assert
        assertNotNull(userDetails);
        assertFalse(userDetails.getAuthorities().isEmpty());
    }

    @Test
    void testLoadUserByUsernamePasswordPreserved() {
        // Arrange
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("admin");

        // Assert
        assertEquals(user.getPassword(), userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsernameAccountNonExpired() {
        // Arrange
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("admin");

        // Assert
        assertTrue(userDetails.isAccountNonExpired());
    }
}
