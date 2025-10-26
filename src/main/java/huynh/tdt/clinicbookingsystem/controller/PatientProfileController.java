package huynh.tdt.clinicbookingsystem.controller;

import huynh.tdt.clinicbookingsystem.entity.Patient;
import huynh.tdt.clinicbookingsystem.entity.User;
import huynh.tdt.clinicbookingsystem.repository.UserRepository;
import huynh.tdt.clinicbookingsystem.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/patient")
public class PatientProfileController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Display patient profile page
     */
    @GetMapping("/profile")
    public String viewProfile(Model model) {
        try {
            // Get current user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Get or create patient profile
            Patient patient = patientService.getOrCreatePatient(user.getId());

            model.addAttribute("patient", patient);
            model.addAttribute("user", user);
            model.addAttribute("username", username);

            return "patient-profile";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load profile: " + e.getMessage());
            return "patient-profile";
        }
    }

    /**
     * Update patient profile
     */
    @PostMapping("/profile/update")
    public String updateProfile(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "dateOfBirth", required = false) String dateOfBirth,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "email", required = false) String email,
            RedirectAttributes redirectAttributes) {
        try {
            // Get current user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Get patient profile
            Patient patient = patientService.getOrCreatePatient(user.getId());

            // Update user fields
            if (firstName != null && !firstName.isEmpty()) {
                user.setFullName(firstName + (lastName != null && !lastName.isEmpty() ? " " + lastName : ""));
            }
            if (email != null && !email.isEmpty()) {
                user.setEmail(email);
            }

            // Update patient fields
            if (dateOfBirth != null && !dateOfBirth.isEmpty()) {
                patient.setDateOfBirth(java.time.LocalDate.parse(dateOfBirth));
            }
            if (gender != null && !gender.isEmpty()) {
                patient.setGender(Patient.Gender.valueOf(gender));
            }
            if (phone != null && !phone.isEmpty()) {
                patient.setPhone(phone);
            }
            if (address != null && !address.isEmpty()) {
                patient.setAddress(address);
            }

            // Save updated data
            userRepository.save(user);
            patientService.updatePatient(patient);

            redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
            return "redirect:/patient/profile";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update profile: " + e.getMessage());
            return "redirect:/patient/profile";
        }
    }
}

