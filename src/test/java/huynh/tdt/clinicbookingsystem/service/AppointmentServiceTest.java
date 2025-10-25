package huynh.tdt.clinicbookingsystem.service;

import huynh.tdt.clinicbookingsystem.dto.AppointmentResponse;
import huynh.tdt.clinicbookingsystem.dto.BookAppointmentRequest;
import huynh.tdt.clinicbookingsystem.entity.*;
import huynh.tdt.clinicbookingsystem.repository.AppointmentRepository;
import huynh.tdt.clinicbookingsystem.repository.DoctorRepository;
import huynh.tdt.clinicbookingsystem.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    private Patient testPatient;
    private Doctor testDoctor;
    private User testDoctorUser;
    private User testPatientUser;
    private Appointment testAppointment;

    @BeforeEach
    void setUp() {
        // Setup test data
        testPatientUser = new User();
        testPatientUser.setId(1L);
        testPatientUser.setUsername("patient01");
        testPatientUser.setFullName("Alice Nguyen");
        testPatientUser.setEmail("alice@example.com");

        testDoctorUser = new User();
        testDoctorUser.setId(2L);
        testDoctorUser.setUsername("drsmith");
        testDoctorUser.setFullName("Dr. John Smith");
        testDoctorUser.setEmail("drsmith@clinic.com");

        testPatient = new Patient();
        testPatient.setId(1L);
        testPatient.setUser(testPatientUser);

        testDoctor = new Doctor();
        testDoctor.setId(1L);
        testDoctor.setUser(testDoctorUser);
        testDoctor.setSpecialization("Cardiology");
        testDoctor.setExperienceYears(5);
        testDoctor.setAvailable(true);

        testAppointment = new Appointment();
        testAppointment.setId(1L);
        testAppointment.setPatient(testPatient);
        testAppointment.setDoctor(testDoctor);
        testAppointment.setAppointmentTime(LocalDateTime.now().plusDays(1));
        testAppointment.setStatus(Appointment.AppointmentStatus.PENDING);
        testAppointment.setNotes("Heart checkup");
    }

    @Test
    void testBookAppointment_Success() {
        // Arrange
        BookAppointmentRequest request = new BookAppointmentRequest();
        request.setDoctorId(1L);
        request.setAppointmentTime(LocalDateTime.now().plusDays(1));
        request.setNotes("Heart checkup");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(testDoctor));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(testAppointment);

        // Act
        AppointmentResponse response = appointmentService.bookAppointment(1L, request);

        // Assert
        assertNotNull(response);
        assertEquals("Alice Nguyen", response.getPatientName());
        assertEquals("Dr. John Smith", response.getDoctorName());
        assertEquals("Cardiology", response.getSpecialization());
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void testBookAppointment_PatientNotFound() {
        // Arrange
        BookAppointmentRequest request = new BookAppointmentRequest();
        request.setDoctorId(1L);
        request.setAppointmentTime(LocalDateTime.now().plusDays(1));

        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            appointmentService.bookAppointment(1L, request);
        });
    }

    @Test
    void testBookAppointment_DoctorNotFound() {
        // Arrange
        BookAppointmentRequest request = new BookAppointmentRequest();
        request.setDoctorId(1L);
        request.setAppointmentTime(LocalDateTime.now().plusDays(1));

        when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            appointmentService.bookAppointment(1L, request);
        });
    }

    @Test
    void testBookAppointment_PastDateTime() {
        // Arrange
        BookAppointmentRequest request = new BookAppointmentRequest();
        request.setDoctorId(1L);
        request.setAppointmentTime(LocalDateTime.now().minusDays(1));

        when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(testDoctor));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            appointmentService.bookAppointment(1L, request);
        });
    }

    @Test
    void testGetPatientAppointments() {
        // Arrange
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(testAppointment);

        when(appointmentRepository.findByPatientIdOrderByAppointmentTimeDesc(1L))
                .thenReturn(appointments);

        // Act
        List<AppointmentResponse> responses = appointmentService.getPatientAppointments(1L);

        // Assert
        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Alice Nguyen", responses.get(0).getPatientName());
        verify(appointmentRepository, times(1)).findByPatientIdOrderByAppointmentTimeDesc(1L);
    }

    @Test
    void testGetDoctorAppointments() {
        // Arrange
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(testAppointment);

        when(appointmentRepository.findByDoctorIdOrderByAppointmentTimeDesc(1L))
                .thenReturn(appointments);

        // Act
        List<AppointmentResponse> responses = appointmentService.getDoctorAppointments(1L);

        // Assert
        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Dr. John Smith", responses.get(0).getDoctorName());
        verify(appointmentRepository, times(1)).findByDoctorIdOrderByAppointmentTimeDesc(1L);
    }

    @Test
    void testGetAppointmentById() {
        // Arrange
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(testAppointment));

        // Act
        AppointmentResponse response = appointmentService.getAppointmentById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Alice Nguyen", response.getPatientName());
        verify(appointmentRepository, times(1)).findById(1L);
    }

    @Test
    void testCancelAppointment_Success() {
        // Arrange
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(testAppointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(testAppointment);

        // Act
        AppointmentResponse response = appointmentService.cancelAppointment(1L);

        // Assert
        assertNotNull(response);
        assertEquals("CANCELLED", response.getStatus());
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void testCancelAppointment_AlreadyCancelled() {
        // Arrange
        testAppointment.setStatus(Appointment.AppointmentStatus.CANCELLED);
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(testAppointment));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            appointmentService.cancelAppointment(1L);
        });
    }

    @Test
    void testCancelAppointment_Completed() {
        // Arrange
        testAppointment.setStatus(Appointment.AppointmentStatus.COMPLETED);
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(testAppointment));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            appointmentService.cancelAppointment(1L);
        });
    }

    @Test
    void testConfirmAppointment_Success() {
        // Arrange
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(testAppointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(testAppointment);

        // Act
        AppointmentResponse response = appointmentService.confirmAppointment(1L);

        // Assert
        assertNotNull(response);
        assertEquals("CONFIRMED", response.getStatus());
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void testCompleteAppointment_Success() {
        // Arrange
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(testAppointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(testAppointment);

        // Act
        AppointmentResponse response = appointmentService.completeAppointment(1L);

        // Assert
        assertNotNull(response);
        assertEquals("COMPLETED", response.getStatus());
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }
}

