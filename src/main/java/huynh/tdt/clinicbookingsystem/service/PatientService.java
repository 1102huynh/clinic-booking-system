package huynh.tdt.clinicbookingsystem.service;

import huynh.tdt.clinicbookingsystem.entity.Patient;
import huynh.tdt.clinicbookingsystem.entity.User;
import huynh.tdt.clinicbookingsystem.repository.PatientRepository;
import huynh.tdt.clinicbookingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Get or create a patient for a user
     */
    @Transactional
    public Patient getOrCreatePatient(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return patientRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Patient patient = new Patient();
                    patient.setUser(user);
                    return patientRepository.save(patient);
                });
    }

    /**
     * Get patient by user ID
     */
    public Patient getPatientByUserId(Long userId) {
        return patientRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Patient not found for user"));
    }

    /**
     * Get patient by ID
     */
    public Patient getPatientById(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    /**
     * Update patient profile
     */
    @Transactional
    public Patient updatePatientProfile(Long patientId, Patient patientDetails) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        if (patientDetails.getDateOfBirth() != null) {
            patient.setDateOfBirth(patientDetails.getDateOfBirth());
        }
        if (patientDetails.getGender() != null) {
            patient.setGender(patientDetails.getGender());
        }
        if (patientDetails.getPhone() != null && !patientDetails.getPhone().isEmpty()) {
            patient.setPhone(patientDetails.getPhone());
        }
        if (patientDetails.getAddress() != null && !patientDetails.getAddress().isEmpty()) {
            patient.setAddress(patientDetails.getAddress());
        }

        return patientRepository.save(patient);
    }
}

