package huynh.tdt.clinicbookingsystem.controller;

import huynh.tdt.clinicbookingsystem.entity.User;
import huynh.tdt.clinicbookingsystem.repository.UserRepository;
import huynh.tdt.clinicbookingsystem.repository.AppointmentRepository;
import huynh.tdt.clinicbookingsystem.repository.PatientRepository;
import huynh.tdt.clinicbookingsystem.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    /**
     * Admin dashboard overview
     */
    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        try {
            // Get current admin user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            model.addAttribute("username", username);

            // Get statistics
            long totalUsers = userRepository.count();
            long totalAppointments = appointmentRepository.count();
            long totalPatients = patientRepository.count();
            long totalDoctors = doctorRepository.count();

            model.addAttribute("totalUsers", totalUsers);
            model.addAttribute("totalAppointments", totalAppointments);
            model.addAttribute("totalPatients", totalPatients);
            model.addAttribute("totalDoctors", totalDoctors);

            // Get recent appointments
            List<?> recentAppointments = appointmentRepository.findAll().stream()
                    .sorted((a, b) -> b.getAppointmentTime().compareTo(a.getAppointmentTime()))
                    .limit(5)
                    .toList();
            model.addAttribute("recentAppointments", recentAppointments);

            return "admin-dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load admin dashboard: " + e.getMessage());
            return "admin-dashboard";
        }
    }

    /**
     * List all users
     */
    @GetMapping("/users")
    public String listUsers(Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            model.addAttribute("username", username);

            List<User> users = userRepository.findAll();
            model.addAttribute("users", users);

            return "admin-users";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load users: " + e.getMessage());
            return "admin-users";
        }
    }

    /**
     * View user details
     */
    @GetMapping("/users/{userId}")
    public String viewUser(@PathVariable Long userId, Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            model.addAttribute("username", username);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            model.addAttribute("user", user);

            return "admin-user-detail";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load user: " + e.getMessage());
            return "admin-user-detail";
        }
    }

    /**
     * Enable/disable user
     */
    @PostMapping("/users/{userId}/toggle-status")
    public String toggleUserStatus(@PathVariable Long userId, RedirectAttributes redirectAttributes) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            user.setEnabled(!user.isEnabled());
            userRepository.save(user);

            String status = user.isEnabled() ? "enabled" : "disabled";
            redirectAttributes.addFlashAttribute("success", "User " + status + " successfully!");
            return "redirect:/admin/users/" + userId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update user: " + e.getMessage());
            return "redirect:/admin/users";
        }
    }

    /**
     * View all appointments
     */
    @GetMapping("/appointments")
    public String listAppointments(Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            model.addAttribute("username", username);

            var appointments = appointmentRepository.findAll().stream()
                    .sorted((a, b) -> b.getAppointmentTime().compareTo(a.getAppointmentTime()))
                    .toList();
            model.addAttribute("appointments", appointments);

            return "admin-appointments";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load appointments: " + e.getMessage());
            return "admin-appointments";
        }
    }

    /**
     * View appointment details
     */
    @GetMapping("/appointments/{appointmentId}")
    public String viewAppointment(@PathVariable Long appointmentId, Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            model.addAttribute("username", username);

            var appointment = appointmentRepository.findById(appointmentId)
                    .orElseThrow(() -> new RuntimeException("Appointment not found"));
            model.addAttribute("appointment", appointment);

            return "admin-appointment-detail";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load appointment: " + e.getMessage());
            return "admin-appointment-detail";
        }
    }

    /**
     * View system statistics
     */
    @GetMapping("/statistics")
    public String viewStatistics(Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            model.addAttribute("username", username);

            // Basic statistics
            long totalUsers = userRepository.count();
            long totalAppointments = appointmentRepository.count();
            long totalPatients = patientRepository.count();
            long totalDoctors = doctorRepository.count();

            // Appointment status counts
            var allAppointments = appointmentRepository.findAll();
            long pendingCount = allAppointments.stream()
                    .filter(a -> a.getStatus().toString().equals("PENDING"))
                    .count();
            long confirmedCount = allAppointments.stream()
                    .filter(a -> a.getStatus().toString().equals("CONFIRMED"))
                    .count();
            long completedCount = allAppointments.stream()
                    .filter(a -> a.getStatus().toString().equals("COMPLETED"))
                    .count();
            long cancelledCount = allAppointments.stream()
                    .filter(a -> a.getStatus().toString().equals("CANCELLED"))
                    .count();

            model.addAttribute("totalUsers", totalUsers);
            model.addAttribute("totalAppointments", totalAppointments);
            model.addAttribute("totalPatients", totalPatients);
            model.addAttribute("totalDoctors", totalDoctors);
            model.addAttribute("pendingCount", pendingCount);
            model.addAttribute("confirmedCount", confirmedCount);
            model.addAttribute("completedCount", completedCount);
            model.addAttribute("cancelledCount", cancelledCount);

            return "admin-statistics";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load statistics: " + e.getMessage());
            return "admin-statistics";
        }
    }
}

