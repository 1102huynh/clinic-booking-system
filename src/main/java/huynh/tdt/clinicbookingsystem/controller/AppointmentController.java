package huynh.tdt.clinicbookingsystem.controller;

import huynh.tdt.clinicbookingsystem.dto.AppointmentResponse;
import huynh.tdt.clinicbookingsystem.dto.BookAppointmentRequest;
import huynh.tdt.clinicbookingsystem.dto.DoctorResponse;
import huynh.tdt.clinicbookingsystem.entity.Patient;
import huynh.tdt.clinicbookingsystem.entity.User;
import huynh.tdt.clinicbookingsystem.repository.UserRepository;
import huynh.tdt.clinicbookingsystem.service.AppointmentService;
import huynh.tdt.clinicbookingsystem.service.DoctorService;
import huynh.tdt.clinicbookingsystem.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Display book appointment page
     */
    @GetMapping("/book")
    public String showBookAppointmentPage(Model model) {
        // Get current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Get or create patient profile
        Patient patient = patientService.getOrCreatePatient(user.getId());

        // Get all available doctors
        List<DoctorResponse> doctors = doctorService.getAllAvailableDoctors();

        // Get all specializations
        List<String> specializations = doctorService.getAllSpecializations();

        model.addAttribute("patient", patient);
        model.addAttribute("doctors", doctors);
        model.addAttribute("specializations", specializations);
        model.addAttribute("bookAppointmentRequest", new BookAppointmentRequest());

        return "book-appointment";
    }

    /**
     * Filter doctors by specialization (AJAX endpoint)
     */
    @GetMapping("/api/doctors")
    @ResponseBody
    public List<DoctorResponse> getDoctorsBySpecialization(
            @RequestParam(required = false) String specialization) {
        if (specialization != null && !specialization.isEmpty()) {
            return doctorService.getDoctorsBySpecialization(specialization);
        }
        return doctorService.getAllAvailableDoctors();
    }

    /**
     * Book an appointment
     */
    @PostMapping("/book")
    public String bookAppointment(
            @RequestParam Long doctorId,
            @RequestParam String appointmentDate,
            @RequestParam String appointmentTime,
            @RequestParam(required = false) String notes,
            RedirectAttributes redirectAttributes) {
        try {
            // Get current user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Get patient
            Patient patient = patientService.getPatientByUserId(user.getId());

            // Parse date and time
            LocalDate date = LocalDate.parse(appointmentDate);
            LocalTime time = LocalTime.parse(appointmentTime);
            LocalDateTime appointmentDateTime = LocalDateTime.of(date, time);

            // Create booking request
            BookAppointmentRequest request = new BookAppointmentRequest();
            request.setDoctorId(doctorId);
            request.setAppointmentTime(appointmentDateTime);
            request.setNotes(notes);

            // Book appointment
            AppointmentResponse response = appointmentService.bookAppointment(patient.getId(), request);

            redirectAttributes.addFlashAttribute("success",
                    "Appointment booked successfully! Appointment ID: " + response.getId());
            return "redirect:/appointment/my-appointments";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Failed to book appointment: " + e.getMessage());
            return "redirect:/appointment/book";
        }
    }

    /**
     * View patient's appointments
     */
    @GetMapping("/my-appointments")
    public String viewMyAppointments(Model model) {
        try {
            // Get current user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Get patient
            Patient patient = patientService.getPatientByUserId(user.getId());

            // Get patient's appointments
            List<AppointmentResponse> appointments = appointmentService.getPatientAppointments(patient.getId());

            model.addAttribute("appointments", appointments);
            model.addAttribute("username", username);

            return "my-appointments";
        } catch (Exception e) {
            // Always add an empty list to prevent null pointer in template
            model.addAttribute("appointments", new ArrayList<>());
            model.addAttribute("error", "Failed to load appointments: " + e.getMessage());
            return "my-appointments";
        }
    }

    /**
     * View appointment details
     */
    @GetMapping("/{appointmentId}")
    public String viewAppointmentDetails(@PathVariable Long appointmentId, Model model) {
        try {
            AppointmentResponse appointment = appointmentService.getAppointmentById(appointmentId);
            model.addAttribute("appointment", appointment);
            return "appointment-details";
        } catch (Exception e) {
            model.addAttribute("error", "Appointment not found");
            return "appointment-details";
        }
    }

    /**
     * Cancel an appointment
     */
    @PostMapping("/{appointmentId}/cancel")
    public String cancelAppointment(@PathVariable Long appointmentId, RedirectAttributes redirectAttributes) {
        try {
            appointmentService.cancelAppointment(appointmentId);
            redirectAttributes.addFlashAttribute("success", "Appointment cancelled successfully");
            return "redirect:/appointment/my-appointments";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to cancel appointment: " + e.getMessage());
            return "redirect:/appointment/my-appointments";
        }
    }
}
