package huynh.tdt.clinicbookingsystem.service;

import huynh.tdt.clinicbookingsystem.entity.Patient;
import huynh.tdt.clinicbookingsystem.entity.User;
import huynh.tdt.clinicbookingsystem.repository.PatientRepository;
import huynh.tdt.clinicbookingsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PatientService patientService;

    private User testUser;
    private Patient testPatient;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("patient01");
        testUser.setFullName("Alice Nguyen");
        testUser.setEmail("alice@example.com");

        testPatient = new Patient();
        testPatient.setId(1L);
        testPatient.setUser(testUser);
        testPatient.setDateOfBirth(LocalDate.of(1995, 4, 12));
        testPatient.setPhone("0988888888");
        testPatient.setAddress("Ho Chi Minh City");
    }

    @Test
    void testGetOrCreatePatient_Exists() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(patientRepository.findByUserId(1L)).thenReturn(Optional.of(testPatient));

        // Act
        Patient result = patientService.getOrCreatePatient(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(patientRepository, times(1)).findByUserId(1L);
        verify(patientRepository, never()).save(any(Patient.class));
    }

    @Test
    void testGetOrCreatePatient_Create() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(patientRepository.findByUserId(1L)).thenReturn(Optional.empty());
        when(patientRepository.save(any(Patient.class))).thenReturn(testPatient);

        // Act
        Patient result = patientService.getOrCreatePatient(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testUser.getId(), result.getUser().getId());
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void testGetOrCreatePatient_UserNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            patientService.getOrCreatePatient(1L);
        });
    }

    @Test
    void testGetPatientByUserId() {
        // Arrange
        when(patientRepository.findByUserId(1L)).thenReturn(Optional.of(testPatient));

        // Act
        Patient result = patientService.getPatientByUserId(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Alice Nguyen", result.getUser().getFullName());
        verify(patientRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testGetPatientByUserId_NotFound() {
        // Arrange
        when(patientRepository.findByUserId(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            patientService.getPatientByUserId(1L);
        });
    }

    @Test
    void testGetPatientById() {
        // Arrange
        when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));

        // Act
        Patient result = patientService.getPatientById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdatePatientProfile() {
        // Arrange
        Patient updatedPatient = new Patient();
        updatedPatient.setPhone("0912345678");
        updatedPatient.setAddress("Hanoi");
        updatedPatient.setGender(Patient.Gender.FEMALE);

        when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));
        when(patientRepository.save(any(Patient.class))).thenReturn(testPatient);

        // Act
        Patient result = patientService.updatePatientProfile(1L, updatedPatient);

        // Assert
        assertNotNull(result);
        verify(patientRepository, times(1)).save(any(Patient.class));
    }
}

