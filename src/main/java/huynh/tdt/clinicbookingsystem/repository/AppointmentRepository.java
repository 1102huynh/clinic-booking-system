package huynh.tdt.clinicbookingsystem.repository;

import huynh.tdt.clinicbookingsystem.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByDoctorId(Long doctorId);
    List<Appointment> findByPatientIdOrderByAppointmentTimeDesc(Long patientId);
    List<Appointment> findByDoctorIdOrderByAppointmentTimeDesc(Long doctorId);
    List<Appointment> findByAppointmentTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
}

