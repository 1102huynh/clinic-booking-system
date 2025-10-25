package huynh.tdt.clinicbookingsystem.controller;

import huynh.tdt.clinicbookingsystem.dto.RegisterRequest;
import huynh.tdt.clinicbookingsystem.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/login")
    public String loginPage(Model model) {
        // This method handles both GET requests and forwards from failed login attempts
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(RegisterRequest request, RedirectAttributes redirectAttributes) {
        try {
            boolean registered = registrationService.registerUser(request);

            if (registered) {
                redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
                return "redirect:/auth/login";
            } else {
                redirectAttributes.addFlashAttribute("error", "Registration failed. Username may already exist or passwords don't match.");
                return "redirect:/auth/register";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An unexpected error occurred during registration.");
            return "redirect:/auth/register";
        }
    }
}
