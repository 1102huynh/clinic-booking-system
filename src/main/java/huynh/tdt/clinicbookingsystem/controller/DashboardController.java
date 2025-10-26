package huynh.tdt.clinicbookingsystem.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            model.addAttribute("username", username);

            // Determine user role
            String userRole = "PATIENT"; // default
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                String role = authority.getAuthority();
                if (role.equals("ROLE_DOCTOR")) {
                    userRole = "DOCTOR";
                    break;
                } else if (role.equals("ROLE_ADMIN")) {
                    userRole = "ADMIN";
                    break;
                }
            }
            model.addAttribute("userRole", userRole);
        }
        return "dashboard";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }
}
