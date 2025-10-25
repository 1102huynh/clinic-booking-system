package huynh.tdt.clinicbookingsystem.controller;

import huynh.tdt.clinicbookingsystem.dto.RegisterRequest;
import huynh.tdt.clinicbookingsystem.service.RegistrationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private RegistrationService registrationService;

    @InjectMocks
    private AuthController authController;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Test
    void testLoginPageGet() {
        // Act
        String viewName = authController.loginPage(model);

        // Assert
        assertEquals("login", viewName);
    }

    @Test
    void testRegisterPageGet() {
        // Act
        String viewName = authController.registerPage();

        // Assert
        assertEquals("register", viewName);
    }

    @Test
    void testRegisterUserSuccess() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setUsername("newuser");
        request.setPassword("password123");
        request.setConfirmPassword("password123");

        when(registrationService.registerUser(request)).thenReturn(true);

        // Act
        String result = authController.registerUser(request, redirectAttributes);

        // Assert
        assertTrue(result.contains("redirect:/auth/login"));
        verify(registrationService, times(1)).registerUser(request);
    }

    @Test
    void testRegisterUserFailure() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setUsername("existinguser");
        request.setPassword("password123");
        request.setConfirmPassword("password123");

        when(registrationService.registerUser(request)).thenReturn(false);

        // Act
        String result = authController.registerUser(request, redirectAttributes);

        // Assert
        assertTrue(result.contains("redirect:/auth/register"));
        verify(registrationService, times(1)).registerUser(request);
    }

    @Test
    void testRegisterUserException() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        when(registrationService.registerUser(any())).thenThrow(new RuntimeException("Database error"));

        // Act
        String result = authController.registerUser(request, redirectAttributes);

        // Assert
        assertTrue(result.contains("redirect:/auth/register"));
    }

    @Test
    void testLoginPageReturnsCorrectViewName() {
        // Act
        String viewName = authController.loginPage(model);

        // Assert
        assertNotNull(viewName);
        assertEquals("login", viewName);
    }

    @Test
    void testRegisterPageReturnsCorrectViewName() {
        // Act
        String viewName = authController.registerPage();

        // Assert
        assertNotNull(viewName);
        assertEquals("register", viewName);
    }
}
