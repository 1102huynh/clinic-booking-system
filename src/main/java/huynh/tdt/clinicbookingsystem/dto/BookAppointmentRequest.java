package huynh.tdt.clinicbookingsystem.dto;

import java.time.LocalDateTime;

public class BookAppointmentRequest {
    private Long doctorId;
    private LocalDateTime appointmentTime;
    private String notes;

    public BookAppointmentRequest() {
    }

    public BookAppointmentRequest(Long doctorId, LocalDateTime appointmentTime, String notes) {
        this.doctorId = doctorId;
        this.appointmentTime = appointmentTime;
        this.notes = notes;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

