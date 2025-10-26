package huynh.tdt.clinicbookingsystem.service;

import huynh.tdt.clinicbookingsystem.dto.DoctorResponse;
import huynh.tdt.clinicbookingsystem.entity.Doctor;
import huynh.tdt.clinicbookingsystem.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    /**
     * Get all available doctors
     */
    public List<DoctorResponse> getAllAvailableDoctors() {
        List<Doctor> doctors = doctorRepository.findByAvailableTrue();
        return doctors.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get doctors by specialization
     */
    public List<DoctorResponse> getDoctorsBySpecialization(String specialization) {
        List<Doctor> doctors = doctorRepository.findByAvailableTrueAndSpecialization(specialization);
        return doctors.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get all specializations available
     */
    public List<String> getAllSpecializations() {
        return doctorRepository.findAll()
                .stream()
                .map(Doctor::getSpecialization)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Get a specific doctor by ID
     */
    public DoctorResponse getDoctorById(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return convertToResponse(doctor);
    }

    /**
     * Get doctor entity by user ID (not DTO)
     */
    public Doctor getDoctorByUserId(Long userId) {
        return doctorRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Doctor profile not found for user"));
    }

    /**
     * Convert Doctor entity to DoctorResponse DTO
     */
    private DoctorResponse convertToResponse(Doctor doctor) {
        return new DoctorResponse(
                doctor.getId(),
                doctor.getUser().getFullName(),
                doctor.getSpecialization(),
                doctor.getExperienceYears(),
                doctor.getAvailable()
        );
    }
}
