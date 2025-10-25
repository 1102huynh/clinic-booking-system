package huynh.tdt.clinicbookingsystem.repository;

import huynh.tdt.clinicbookingsystem.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUserId(Long userId);
    List<Doctor> findByAvailableTrue();
    List<Doctor> findBySpecialization(String specialization);
    List<Doctor> findByAvailableTrueAndSpecialization(String specialization);
}

