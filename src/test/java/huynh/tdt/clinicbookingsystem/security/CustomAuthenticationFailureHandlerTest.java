package huynh.tdt.clinicbookingsystem.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomAuthenticationFailureHandlerTest {

    private CustomAuthenticationFailureHandler failureHandler;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @BeforeEach
    void setUp() {
        failureHandler = new CustomAuthenticationFailureHandler();
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testOnAuthenticationFailureRedirect() throws IOException, ServletException {
        AuthenticationException exception = new BadCredentialsException("Bad credentials");

        failureHandler.onAuthenticationFailure(request, response, exception);

        verify(response).sendRedirect("/auth/login?error=true");
    }

    @Test
    void testOnAuthenticationFailureStoresError() throws IOException, ServletException {
        AuthenticationException exception = new BadCredentialsException("Bad credentials");

        failureHandler.onAuthenticationFailure(request, response, exception);

        verify(session).setAttribute(eq("loginError"), anyString());
    }

    @Test
    void testOnAuthenticationFailureMultipleCalls() throws IOException, ServletException {
        AuthenticationException exception = new BadCredentialsException("Bad credentials");

        failureHandler.onAuthenticationFailure(request, response, exception);
        failureHandler.onAuthenticationFailure(request, response, exception);

        verify(response, times(2)).sendRedirect("/auth/login?error=true");
    }
}
