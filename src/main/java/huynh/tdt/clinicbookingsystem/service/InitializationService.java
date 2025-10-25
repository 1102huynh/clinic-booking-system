package huynh.tdt.clinicbookingsystem.service;

import huynh.tdt.clinicbookingsystem.entity.User;
import huynh.tdt.clinicbookingsystem.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

/**
 * Service to initialize admin users with correct BCrypt passwords on startup
 */
@Service
public class InitializationService {
    private static final Logger logger = LoggerFactory.getLogger(InitializationService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initializeAdminUsers() {
        logger.info("Starting admin user initialization...");

        // Initialize admin user
        initializeUser("admin", "admin123", "System Admin", "admin@clinic.com", "ADMIN");
        initializeUser("drsmith", "password123", "Dr. John Smith", "drsmith@clinic.com", "DOCTOR");
        initializeUser("patient01", "password123", "Alice Nguyen", "alice@example.com", "PATIENT");

        logger.info("Admin user initialization completed");
    }

    private void initializeUser(String username, String plainPassword, String fullName, String email, String role) {
        try {
            // Check if user already exists
            var existingUser = userRepository.findByUsername(username);

            if (existingUser.isPresent()) {
                User user = existingUser.get();
                // Update password with correctly encoded hash
                String encodedPassword = passwordEncoder.encode(plainPassword);

                // Only update if the password doesn't already match
                if (!passwordEncoder.matches(plainPassword, user.getPassword())) {
                    user.setPassword(encodedPassword);
                    user.setEnabled(true);
                    userRepository.save(user);
                    logger.info("Updated password for user: {}", username);
                    logger.debug("New hash for {}: {}", username, encodedPassword);
                } else {
                    logger.info("Password for {} is already correct", username);
                }
            } else {
                // Create new user
                User user = new User();
                user.setUsername(username);
                user.setPassword(passwordEncoder.encode(plainPassword));
                user.setFullName(fullName);
                user.setEmail(email);
                user.setRole(role);
                user.setEnabled(true);
                userRepository.save(user);
                logger.info("Created new user: {}", username);
            }
        } catch (Exception e) {
            logger.error("Error initializing user {}: {}", username, e.getMessage(), e);
        }
    }
}

