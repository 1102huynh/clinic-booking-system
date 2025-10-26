package huynh.tdt.clinicbookingsystem.service;

import huynh.tdt.clinicbookingsystem.dto.AppointmentResponse;
import huynh.tdt.clinicbookingsystem.dto.BookAppointmentRequest;
import huynh.tdt.clinicbookingsystem.entity.Appointment;
import huynh.tdt.clinicbookingsystem.entity.Doctor;
import huynh.tdt.clinicbookingsystem.entity.Patient;
import huynh.tdt.clinicbookingsystem.repository.AppointmentRepository;
import huynh.tdt.clinicbookingsystem.repository.DoctorRepository;
import huynh.tdt.clinicbookingsystem.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private EmailService emailService;

    /**
     * Book a new appointment for a patient
     */
    @Transactional
    public AppointmentResponse bookAppointment(Long patientId, BookAppointmentRequest request) {
        // Validate patient exists
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        // Validate doctor exists
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // Validate appointment time is in the future
        if (request.getAppointmentTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Appointment time must be in the future");
        }

        // Create and save appointment
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setNotes(request.getNotes());
        appointment.setStatus(Appointment.AppointmentStatus.PENDING);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return convertToResponse(savedAppointment);
    }

    /**
     * Get all appointments for a patient
     */
    public List<AppointmentResponse> getPatientAppointments(Long patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatientIdOrderByAppointmentTimeDesc(patientId);
        return appointments.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get all appointments for a doctor
     */
    public List<AppointmentResponse> getDoctorAppointments(Long doctorId) {
        List<Appointment> appointments = appointmentRepository.findByDoctorIdOrderByAppointmentTimeDesc(doctorId);
        return appointments.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get a specific appointment by ID
     */
    public AppointmentResponse getAppointmentById(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        return convertToResponse(appointment);
    }

    /**
     * Cancel an appointment
     */
    @Transactional
    public AppointmentResponse cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (appointment.getStatus() == Appointment.AppointmentStatus.CANCELLED) {
            throw new RuntimeException("Appointment is already cancelled");
        }

        if (appointment.getStatus() == Appointment.AppointmentStatus.COMPLETED) {
            throw new RuntimeException("Cannot cancel a completed appointment");
        }

        appointment.setStatus(Appointment.AppointmentStatus.CANCELLED);
        Appointment updatedAppointment = appointmentRepository.save(appointment);

        // Send cancellation email to patient
        try {
            String patientEmail = updatedAppointment.getPatient().getUser().getEmail();
            String patientName = updatedAppointment.getPatient().getUser().getFullName();
            String doctorName = updatedAppointment.getDoctor().getUser().getFullName();
            String appointmentDate = updatedAppointment.getAppointmentTime().toLocalDate().toString();
            String appointmentTime = updatedAppointment.getAppointmentTime().toLocalTime().toString();

            emailService.sendAppointmentCancelledEmail(patientEmail, patientName, doctorName, appointmentDate, appointmentTime);
        } catch (Exception e) {
            // Log but don't fail the transaction if email fails
        }

        return convertToResponse(updatedAppointment);
    }

    /**
     * Confirm an appointment (by admin/doctor)
     */
    @Transactional
    public AppointmentResponse confirmAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (appointment.getStatus() != Appointment.AppointmentStatus.PENDING) {
            throw new RuntimeException("Only pending appointments can be confirmed");
        }

        appointment.setStatus(Appointment.AppointmentStatus.CONFIRMED);
        Appointment updatedAppointment = appointmentRepository.save(appointment);

        // Send confirmation email to patient
        try {
            String patientEmail = updatedAppointment.getPatient().getUser().getEmail();
            String patientName = updatedAppointment.getPatient().getUser().getFullName();
            String doctorName = updatedAppointment.getDoctor().getUser().getFullName();
            String appointmentDate = updatedAppointment.getAppointmentTime().toLocalDate().toString();
            String appointmentTime = updatedAppointment.getAppointmentTime().toLocalTime().toString();

            emailService.sendAppointmentConfirmedEmail(patientEmail, patientName, doctorName, appointmentDate, appointmentTime);
        } catch (Exception e) {
            // Log but don't fail the transaction if email fails
        }

        return convertToResponse(updatedAppointment);
    }

    /**
     * Mark an appointment as completed
     */
    @Transactional
    public AppointmentResponse completeAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (appointment.getStatus() == Appointment.AppointmentStatus.CANCELLED) {
            throw new RuntimeException("Cannot complete a cancelled appointment");
        }

        appointment.setStatus(Appointment.AppointmentStatus.COMPLETED);
        Appointment updatedAppointment = appointmentRepository.save(appointment);

        // Send completion email to patient
        try {
            String patientEmail = updatedAppointment.getPatient().getUser().getEmail();
            String patientName = updatedAppointment.getPatient().getUser().getFullName();
            String doctorName = updatedAppointment.getDoctor().getUser().getFullName();
            String appointmentDate = updatedAppointment.getAppointmentTime().toLocalDate().toString();

            emailService.sendAppointmentCompletedEmail(patientEmail, patientName, doctorName, appointmentDate);
        } catch (Exception e) {
            // Log but don't fail the transaction if email fails
        }

        return convertToResponse(updatedAppointment);
    }

    /**
     * Convert Appointment entity to AppointmentResponse DTO
     */
    private AppointmentResponse convertToResponse(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getPatient().getUser().getFullName(),
                appointment.getDoctor().getUser().getFullName(),
                appointment.getDoctor().getSpecialization(),
                appointment.getAppointmentTime(),
                appointment.getStatus().toString(),
                appointment.getNotes(),
                appointment.getCreatedAt()
        );
    }
}

