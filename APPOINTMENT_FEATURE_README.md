# Book Appointment Feature - Documentation

## Overview
The Book Appointment feature allows patients to schedule appointments with available doctors in the clinic. This feature includes doctor search/filtering, appointment scheduling, and appointment management capabilities.

## Features Implemented

### 1. **Book Appointment**
- Browse all available doctors
- Filter doctors by specialization
- Select preferred date and time
- Add notes/reason for visit
- Real-time validation

### 2. **View Appointments**
- View all booked appointments
- See appointment status (Pending, Confirmed, Cancelled, Completed)
- Filter appointments by status
- View detailed appointment information
- Cancel appointments (if not completed/cancelled)

### 3. **Find Doctors**
- Search doctors by specialization
- View doctor details (name, specialization, experience years)
- See doctor availability status
- AJAX-based filtering without page reload

## Database Schema

### Tables Created/Used

#### appointments
```sql
- id (Primary Key)
- patient_id (Foreign Key to patients)
- doctor_id (Foreign Key to doctors)
- appointment_time (DateTime)
- status (ENUM: PENDING, CONFIRMED, CANCELLED, COMPLETED)
- notes (Text)
- created_at (Timestamp)
```

#### patients
```sql
- id (Primary Key)
- user_id (Foreign Key to users, Unique)
- date_of_birth (Date)
- gender (ENUM: MALE, FEMALE, OTHER)
- phone (String)
- address (String)
```

#### doctors
```sql
- id (Primary Key)
- user_id (Foreign Key to users, Unique)
- specialization (String)
- experience_years (Integer)
- available (Boolean)
```

## Java Classes Created

### Entities
1. **Appointment.java** - Main appointment entity with status enum
2. **Patient.java** - Patient profile linked to user
3. **Doctor.java** - Doctor profile linked to user

### DTOs (Data Transfer Objects)
1. **BookAppointmentRequest.java** - Request payload for booking
2. **AppointmentResponse.java** - Response with appointment details
3. **DoctorResponse.java** - Doctor info for UI display

### Repositories
1. **AppointmentRepository.java** - CRUD operations for appointments
2. **PatientRepository.java** - Patient data access
3. **DoctorRepository.java** - Doctor data access with filtering

### Services
1. **AppointmentService.java** - Business logic for appointments
   - `bookAppointment()` - Create new appointment
   - `getPatientAppointments()` - Retrieve patient's appointments
   - `getDoctorAppointments()` - Retrieve doctor's appointments
   - `cancelAppointment()` - Cancel pending appointments
   - `confirmAppointment()` - Confirm pending appointments
   - `completeAppointment()` - Mark as completed

2. **DoctorService.java** - Doctor-related operations
   - `getAllAvailableDoctors()` - Get all available doctors
   - `getDoctorsBySpecialization()` - Filter by specialty
   - `getAllSpecializations()` - List unique specializations
   - `getDoctorById()` - Get specific doctor

3. **PatientService.java** - Patient profile management
   - `getOrCreatePatient()` - Auto-create patient if needed
   - `getPatientByUserId()` - Retrieve patient profile
   - `updatePatientProfile()` - Update patient details

### Controller
**AppointmentController.java** - REST/Web endpoints
- `GET /appointment/book` - Show booking form
- `POST /appointment/book` - Submit booking
- `GET /appointment/my-appointments` - View patient's appointments
- `GET /appointment/{id}` - View appointment details
- `POST /appointment/{id}/cancel` - Cancel appointment
- `GET /appointment/api/doctors` - AJAX endpoint for doctor filtering

## HTML Templates

### 1. **book-appointment.html**
- Clean form with doctor selection cards
- Date and time picker
- Specialization filter dropdown
- AJAX-powered doctor filtering
- Responsive design with hover effects
- Form validation

### 2. **my-appointments.html**
- List of all patient appointments
- Status badges with color coding
- Filter by appointment status
- Cancel appointment button (disabled for completed/cancelled)
- Empty state message
- Responsive card layout

### 3. **appointment-details.html**
- Detailed appointment information
- Doctor and patient info
- Appointment time and booking date
- Status display
- Notes section
- Cancel button with confirmation

## API Endpoints

### Web Endpoints
```
GET  /appointment/book                      - Show booking form
POST /appointment/book                      - Submit new appointment
GET  /appointment/my-appointments           - View user's appointments
GET  /appointment/{appointmentId}           - View appointment details
POST /appointment/{appointmentId}/cancel    - Cancel appointment
```

### AJAX Endpoints
```
GET  /appointment/api/doctors?specialization=Cardiology  - Filter doctors
```

## Usage Guide

### For Patients

#### 1. Book an Appointment
1. Login to the system
2. Click "Book Appointment" on dashboard
3. Select a doctor from the list or filter by specialization
4. Choose appointment date and time
5. (Optional) Add notes about your visit
6. Click "Book Appointment"

#### 2. View Your Appointments
1. Click "My Appointments" in the navbar
2. See all your upcoming and past appointments
3. Filter by status using the filter buttons
4. Click "View Details" to see full information
5. Click "Cancel" to cancel pending appointments

### For Administrators

#### 1. Confirm Appointments
- Appointments are created in PENDING status
- Use admin panel to confirm appointments

#### 2. Mark Completed
- Use admin panel to mark appointments as COMPLETED
- Cannot be cancelled once completed

## Business Rules

1. **Appointment Validation**
   - Appointment time must be in the future
   - Patient and doctor must exist
   - Doctor must be available

2. **Status Transitions**
   - PENDING → CONFIRMED → COMPLETED
   - Any status → CANCELLED (except COMPLETED)
   - Cannot cancel completed/cancelled appointments

3. **Doctor Availability**
   - Only available doctors shown in booking
   - Marked by `available` flag in doctors table

4. **Patient Auto-Creation**
   - Patient profile created automatically on first booking attempt
   - User can later update their profile details

## Error Handling

### Common Errors
- "Patient not found" - User must be logged in
- "Doctor not found" - Doctor ID is invalid
- "Appointment time must be in the future" - Date/time validation failed
- "Only pending appointments can be confirmed" - Wrong status
- "Cannot cancel a completed appointment" - Status conflict

## Testing

Comprehensive unit tests included:
- **AppointmentServiceTest** - 13 test methods
- **DoctorServiceTest** - 6 test methods
- **PatientServiceTest** - 7 test methods

All tests use Mockito for mocking dependencies.

## Database Setup

Run the migration script to set up indexes:
```sql
src/main/resources/scriptssql/appointment_feature_setup.sql
```

This creates indexes for optimal query performance.

## UI/UX Features

1. **Responsive Design**
   - Works on desktop, tablet, and mobile
   - Flexible grid layout

2. **Visual Feedback**
   - Hover effects on cards and buttons
   - Status badges with color coding
   - Loading indicators

3. **Accessibility**
   - Clear labels and instructions
   - Form validation messages
   - Confirmation dialogs for destructive actions

4. **Performance**
   - AJAX filtering prevents page reloads
   - Optimized queries with indexes
   - Lazy loading relationships in JPA

## Future Enhancements

1. **Appointment Reminders**
   - Email notifications before appointment
   - SMS reminders

2. **Rescheduling**
   - Allow patients to reschedule appointments
   - Check doctor availability

3. **Availability Calendar**
   - Show doctor's availability calendar
   - Block out non-available times

4. **Advanced Filtering**
   - Filter by experience years
   - Sort by rating/reviews

5. **Booking Analytics**
   - Track most booked doctors
   - Appointment statistics

## Dependencies

- Spring Boot Data JPA
- Spring Web
- Spring Security
- MySQL Database
- Thymeleaf Template Engine
- JUnit 5 & Mockito for testing

## Support

For issues or questions, refer to:
- Appointment-related error logs in `logs/clinic-app.log`
- Test cases for expected behavior
- Controller class for endpoint documentation

