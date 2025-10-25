package huynh.tdt.clinicbookingsystem.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                       AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "Invalid username or password. Please try again.";

        // Provide more specific error messages based on the exception type
        if (exception instanceof DisabledException) {
            errorMessage = "Your account has been disabled. Please contact support.";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = "Username not found or account is disabled.";
        } else if (exception instanceof BadCredentialsException) {
            errorMessage = "Invalid username or password. Please try again.";
        }

        request.getSession().setAttribute("loginError", errorMessage);
        response.sendRedirect("/auth/login?error=true");
    }
}
