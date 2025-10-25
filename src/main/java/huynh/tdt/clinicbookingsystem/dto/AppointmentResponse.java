package huynh.tdt.clinicbookingsystem.dto;

import java.time.LocalDateTime;

public class AppointmentResponse {
    private Long id;
    private String patientName;
    private String doctorName;
    private String specialization;
    private LocalDateTime appointmentTime;
    private String status;
    private String notes;
    private LocalDateTime createdAt;

    public AppointmentResponse() {
    }

    public AppointmentResponse(Long id, String patientName, String doctorName, String specialization,
                               LocalDateTime appointmentTime, String status, String notes, LocalDateTime createdAt) {
        this.id = id;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.notes = notes;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

