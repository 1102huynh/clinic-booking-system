package huynh.tdt.clinicbookingsystem.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomAuthenticationSuccessHandlerTest {

    private CustomAuthenticationSuccessHandler successHandler;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        successHandler = new CustomAuthenticationSuccessHandler();
    }

    @Test
    void testOnAuthenticationSuccessRedirect() throws IOException, ServletException {
        // Act
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // Assert
        verify(response).sendRedirect("/dashboard");
    }

    @Test
    void testOnAuthenticationSuccessMultipleCalls() throws IOException, ServletException {
        // Act
        successHandler.onAuthenticationSuccess(request, response, authentication);
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // Assert
        verify(response, times(2)).sendRedirect("/dashboard");
    }

    @Test
    void testOnAuthenticationSuccessNotToLogin() throws IOException, ServletException {
        // Act
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // Assert
        verify(response, never()).sendRedirect("/auth/login");
    }
}
