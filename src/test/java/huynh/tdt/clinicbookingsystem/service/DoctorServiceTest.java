package huynh.tdt.clinicbookingsystem.service;

import huynh.tdt.clinicbookingsystem.dto.DoctorResponse;
import huynh.tdt.clinicbookingsystem.entity.Doctor;
import huynh.tdt.clinicbookingsystem.entity.User;
import huynh.tdt.clinicbookingsystem.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;

    private Doctor testDoctor1;
    private Doctor testDoctor2;
    private User testUser1;
    private User testUser2;

    @BeforeEach
    void setUp() {
        testUser1 = new User();
        testUser1.setId(1L);
        testUser1.setFullName("Dr. John Smith");
        testUser1.setEmail("drsmith@clinic.com");

        testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setFullName("Dr. Jane Doe");
        testUser2.setEmail("drdoe@clinic.com");

        testDoctor1 = new Doctor();
        testDoctor1.setId(1L);
        testDoctor1.setUser(testUser1);
        testDoctor1.setSpecialization("Cardiology");
        testDoctor1.setExperienceYears(5);
        testDoctor1.setAvailable(true);

        testDoctor2 = new Doctor();
        testDoctor2.setId(2L);
        testDoctor2.setUser(testUser2);
        testDoctor2.setSpecialization("Neurology");
        testDoctor2.setExperienceYears(8);
        testDoctor2.setAvailable(true);
    }

    @Test
    void testGetAllAvailableDoctors() {
        // Arrange
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(testDoctor1);
        doctors.add(testDoctor2);

        when(doctorRepository.findByAvailableTrue()).thenReturn(doctors);

        // Act
        List<DoctorResponse> responses = doctorService.getAllAvailableDoctors();

        // Assert
        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals("Dr. John Smith", responses.get(0).getFullName());
        assertEquals("Cardiology", responses.get(0).getSpecialization());
        verify(doctorRepository, times(1)).findByAvailableTrue();
    }

    @Test
    void testGetDoctorsBySpecialization() {
        // Arrange
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(testDoctor1);

        when(doctorRepository.findByAvailableTrueAndSpecialization("Cardiology"))
                .thenReturn(doctors);

        // Act
        List<DoctorResponse> responses = doctorService.getDoctorsBySpecialization("Cardiology");

        // Assert
        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Cardiology", responses.get(0).getSpecialization());
        verify(doctorRepository, times(1)).findByAvailableTrueAndSpecialization("Cardiology");
    }

    @Test
    void testGetAllSpecializations() {
        // Arrange
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(testDoctor1);
        doctors.add(testDoctor2);

        when(doctorRepository.findAll()).thenReturn(doctors);

        // Act
        List<String> specializations = doctorService.getAllSpecializations();

        // Assert
        assertNotNull(specializations);
        assertEquals(2, specializations.size());
        assertTrue(specializations.contains("Cardiology"));
        assertTrue(specializations.contains("Neurology"));
    }

    @Test
    void testGetDoctorById() {
        // Arrange
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(testDoctor1));

        // Act
        DoctorResponse response = doctorService.getDoctorById(1L);

        // Assert
        assertNotNull(response);
        assertEquals("Dr. John Smith", response.getFullName());
        assertEquals("Cardiology", response.getSpecialization());
        assertEquals(5, response.getExperienceYears());
        verify(doctorRepository, times(1)).findById(1L);
    }

    @Test
    void testGetDoctorById_NotFound() {
        // Arrange
        when(doctorRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            doctorService.getDoctorById(999L);
        });
    }
}

