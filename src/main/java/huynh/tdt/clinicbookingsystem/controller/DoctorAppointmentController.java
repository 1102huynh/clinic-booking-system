package huynh.tdt.clinicbookingsystem.controller;

import huynh.tdt.clinicbookingsystem.dto.AppointmentResponse;
import huynh.tdt.clinicbookingsystem.entity.Doctor;
import huynh.tdt.clinicbookingsystem.entity.User;
import huynh.tdt.clinicbookingsystem.repository.UserRepository;
import huynh.tdt.clinicbookingsystem.service.AppointmentService;
import huynh.tdt.clinicbookingsystem.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorAppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Display doctor's appointments
     */
    @GetMapping("/appointments")
    public String viewDoctorAppointments(Model model) {
        try {
            // Get current user (doctor)
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Get doctor profile
            Doctor doctor = doctorService.getDoctorByUserId(user.getId());

            // Get all appointments for this doctor, sorted by appointment time
            List<AppointmentResponse> appointments = appointmentService.getDoctorAppointments(doctor.getId());

            model.addAttribute("doctor", doctor);
            model.addAttribute("appointments", appointments);
            model.addAttribute("username", username);

            return "doctor-appointments";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load appointments: " + e.getMessage());
            return "doctor-appointments";
        }
    }

    /**
     * Confirm an appointment
     */
    @PostMapping("/appointments/{appointmentId}/confirm")
    public String confirmAppointment(@PathVariable Long appointmentId, RedirectAttributes redirectAttributes) {
        try {
            // Get current user (doctor) to verify authorization
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Doctor doctor = doctorService.getDoctorByUserId(user.getId());

            // Confirm the appointment
            AppointmentResponse response = appointmentService.confirmAppointment(appointmentId);

            redirectAttributes.addFlashAttribute("success",
                    "Appointment confirmed successfully!");
            return "redirect:/doctor/appointments";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Failed to confirm appointment: " + e.getMessage());
            return "redirect:/doctor/appointments";
        }
    }

    /**
     * Reject/cancel an appointment (doctor's action)
     */
    @PostMapping("/appointments/{appointmentId}/reject")
    public String rejectAppointment(@PathVariable Long appointmentId,
                                   @RequestParam(value = "reason", required = false) String reason,
                                   RedirectAttributes redirectAttributes) {
        try {
            // Get current user (doctor)
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Doctor doctor = doctorService.getDoctorByUserId(user.getId());

            // Cancel the appointment
            AppointmentResponse response = appointmentService.cancelAppointment(appointmentId);

            redirectAttributes.addFlashAttribute("success",
                    "Appointment rejected and cancelled successfully!");
            return "redirect:/doctor/appointments";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Failed to reject appointment: " + e.getMessage());
            return "redirect:/doctor/appointments";
        }
    }

    /**
     * Mark appointment as completed
     */
    @PostMapping("/appointments/{appointmentId}/complete")
    public String completeAppointment(@PathVariable Long appointmentId, RedirectAttributes redirectAttributes) {
        try {
            // Get current user (doctor)
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Doctor doctor = doctorService.getDoctorByUserId(user.getId());

            // Mark as completed
            AppointmentResponse response = appointmentService.completeAppointment(appointmentId);

            redirectAttributes.addFlashAttribute("success",
                    "Appointment marked as completed!");
            return "redirect:/doctor/appointments";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Failed to complete appointment: " + e.getMessage());
            return "redirect:/doctor/appointments";
        }
    }
}

