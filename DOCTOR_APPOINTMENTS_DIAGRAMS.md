# Doctor Appointment Management - Visual Diagrams

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                    CLINIC BOOKING SYSTEM                     │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  ┌──────────────────────┐         ┌──────────────────────┐   │
│  │   Patient Side       │         │   Doctor Side (NEW)  │   │
│  ├──────────────────────┤         ├──────────────────────┤   │
│  │ • Login              │         │ • Login              │   │
│  │ • Book Appointment   │────────▶│ • View Appointments  │   │
│  │ • View My Appts      │         │ • Confirm Appt       │   │
│  │ • Cancel Appt        │◀────────│ • Reject Appt        │   │
│  │ • Find Doctor        │         │ • Complete Appt      │   │
│  └──────────────────────┘         └──────────────────────┘   │
│                                                               │
│  ┌──────────────────────────────────────────────────────┐   │
│  │           Appointments Table (Database)              │   │
│  │  ID │ Patient │ Doctor │ Time │ Status   │ Notes    │   │
│  │─────┼─────────┼────────┼──────┼──────────┼──────────│   │
│  │ 1   │ Alice   │ Smith  │ 2pm  │ CONFIRMED│ Checkup  │   │
│  │ 2   │ Bob     │ Jones  │ 3pm  │ PENDING  │ Check    │   │
│  │ 3   │ Carol   │ Lee    │ 4pm  │ COMPLETED│ Done     │   │
│  └──────────────────────────────────────────────────────┘   │
│                                                               │
└─────────────────────────────────────────────────────────────┘
```

## User Flow Diagram

```
┌─────────────┐
│   Doctor    │
│   Login     │
└──────┬──────┘
       │
       ▼
┌──────────────────────────────────┐
│     Dashboard                    │
│  (Role: DOCTOR)                  │
│  ┌──────────────────────────┐    │
│  │ My Appointments Card ←───┼────┼─ Only shown to doctors
│  │ [View Appointments]      │    │
│  └──────────────────────────┘    │
└──────────┬───────────────────────┘
           │
           ▼
┌──────────────────────────────────────────┐
│  /doctor/appointments                    │
│  ┌────────────────────────────────────┐  │
│  │ Patient: Alice Johnson             │  │
│  │ Time: 2025-11-15 2:00 PM           │  │
│  │ Status: 🟡 PENDING                 │  │
│  │ [✓ Confirm] [✗ Reject]            │  │
│  ├────────────────────────────────────┤  │
│  │ Patient: Bob Smith                 │  │
│  │ Time: 2025-11-15 3:00 PM           │  │
│  │ Status: 🟢 CONFIRMED               │  │
│  │ [✓ Mark Complete] [✗ Cancel]      │  │
│  ├────────────────────────────────────┤  │
│  │ Patient: Carol Davis               │  │
│  │ Time: 2025-11-15 4:00 PM           │  │
│  │ Status: ⚫ COMPLETED                │  │
│  │ (No actions available)             │  │
│  └────────────────────────────────────┘  │
└──────┬──────────────────────────────────┘
       │
       ├─ [Confirm] ──▶ PENDING → CONFIRMED
       │
       ├─ [Reject]  ──▶ PENDING/CONFIRMED → CANCELLED
       │
       └─ [Complete]──▶ CONFIRMED → COMPLETED
```

## Appointment State Machine

```
                    ┌──────────────┐
                    │   PENDING    │
                    │   (Yellow)   │
                    └──────┬───────┘
                           │
                ┌──────────┴──────────┐
                │                     │
         [Confirm]            [Reject]
                │                     │
                ▼                     ▼
         ┌────────────┐        ┌───────────┐
         │ CONFIRMED  │        │ CANCELLED │
         │ (Green)    │        │ (Red)     │
         └─────┬──────┘        └───────────┘
               │                     ▲
               │              (No actions)
        [Complete]
         or[Cancel]
               │
               ▼
         ┌────────────┐
         │ COMPLETED  │
         │ (Gray)     │
         └────────────┘
         (No actions)
```

## Component Interaction Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                    Spring Web Tier                              │
├─────────────────────────────────────────────────────────────────┤
│                                                                   │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │         DoctorAppointmentController (NEW)                │   │
│  ├──────────────────────────────────────────────────────────┤   │
│  │ GET  /doctor/appointments                               │   │
│  │ POST /doctor/appointments/{id}/confirm                  │   │
│  │ POST /doctor/appointments/{id}/reject                   │   │
│  │ POST /doctor/appointments/{id}/complete                 │   │
│  └────────────────┬─────────────────────────────────────────┘   │
│                   │                                              │
│                   ▼                                              │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │            AppointmentService (EXISTING)                │   │
│  ├──────────────────────────────────────────────────────────┤   │
│  │ • getDoctorAppointments(doctorId)                       │   │
│  │ • confirmAppointment(appointmentId)                     │   │
│  │ • cancelAppointment(appointmentId)                      │   │
│  │ • completeAppointment(appointmentId)                    │   │
│  └────────────────┬─────────────────────────────────────────┘   │
│                   │                                              │
│                   ▼                                              │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │       AppointmentRepository (EXISTING)                  │   │
│  ├──────────────────────────────────────────────────────────┤   │
│  │ • findByDoctorId(doctorId)                              │   │
│  │ • save(appointment)                                     │   │
│  │ • findById(id)                                          │   │
│  └────────────────┬─────────────────────────────────────────┘   │
│                   │                                              │
│                   ▼                                              │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │          appointments (Database Table)                  │   │
│  └──────────────────────────────────────────────────────────┘   │
│                                                                   │
└─────────────────────────────────────────────────────────────────┘
```

## Data Flow: Confirming an Appointment

```
Doctor Browser          Server                      Database
    │                     │                             │
    │ POST /doctor/       │                             │
    │ appointments/1/     │                             │
    │ confirm             │                             │
    ├────────────────────▶│                             │
    │                     │ Security Check             │
    │                     │ ✓ Authentication OK        │
    │                     │ ✓ ROLE_DOCTOR OK           │
    │                     │                             │
    │                     │ Get Appointment #1         │
    │                     ├────────────────────────────▶│
    │                     │◀────────────────────────────┤
    │                     │ [PENDING appointment]       │
    │                     │                             │
    │                     │ Update Status → CONFIRMED   │
    │                     ├────────────────────────────▶│
    │                     │◀────────────────────────────┤
    │                     │ [Saved]                     │
    │                     │                             │
    │ ◀────────────────────                             │
    │ Redirect + Flash    │                             │
    │ Message             │                             │
    │                     │                             │
    ▼ Redirects to List   │                             │
      (Success shown)     │                             │
```

## Security Layers

```
┌──────────────────────────────────────────────────┐
│  Security & Authorization Checks                 │
├──────────────────────────────────────────────────┤
│                                                   │
│  Layer 1: Spring Security Authentication         │
│  ├─ Verify user logged in                        │
│  └─ Session/Token valid                          │
│                                                   │
│  Layer 2: Spring Security Authorization          │
│  ├─ Check user has ROLE_DOCTOR                   │
│  └─ (@RequestMapping checks on controller)       │
│                                                   │
│  Layer 3: Business Logic Authorization           │
│  ├─ Get current doctor profile                   │
│  ├─ Verify appointment belongs to doctor         │
│  └─ Check appointment can be changed             │
│                                                   │
│  Layer 4: CSRF Protection                        │
│  └─ POST/DELETE requests must include CSRF token │
│                                                   │
└──────────────────────────────────────────────────┘
```

## Feature Integration in Dashboard

```
┌─────────────────────────────────────────────────────────┐
│                    Dashboard                            │
│  (Role-based rendering with Thymeleaf sec:authorize)   │
├─────────────────────────────────────────────────────────┤
│                                                          │
│  sec:authorize="hasAnyRole('PATIENT')"                  │
│  ┌─────────────────────────────────────┐               │
│  │ 📅 Book Appointment                 │               │
│  │ 📋 My Appointments (view & cancel)  │               │
│  │ 👨‍⚕️ Find Doctor (search & browse)    │               │
│  └─────────────────────────────────────┘               │
│          ▲ Shows ONLY to patients                       │
│                                                          │
│  sec:authorize="hasAnyRole('DOCTOR')"                   │
│  ┌─────────────────────────────────────┐               │
│  │ 📅 My Appointments (manage)    ← NEW │               │
│  └─────────────────────────────────────┘               │
│          ▲ Shows ONLY to doctors                        │
│                                                          │
└─────────────────────────────────────────────────────────┘
```

## Test Scenario Timeline

```
Timeline:  Appointment Management Workflow
═══════════════════════════════════════════════════════════════

T=00:00   Patient logs in
          ↓
T=00:05   Patient books appointment with Dr. Smith
          │ Status: PENDING
          ↓
T=00:10   Patient logs out
          ↓
T=00:15   Doctor (Dr. Smith) logs in
          ↓
T=00:20   Doctor navigates to /doctor/appointments
          │ Sees: Patient Alice, 2:00 PM, PENDING
          ↓
T=00:25   Doctor confirms appointment
          │ Status: PENDING → CONFIRMED
          ↓
T=00:30   Doctor logs out
          ↓
T=00:35   Patient logs back in
          ↓
T=00:40   Patient checks "My Appointments"
          │ Sees: Dr. Smith, 2:00 PM, CONFIRMED ✓
          ↓
          SUCCESS: Full workflow verified
```

## File Structure

```
clinic-booking-system/
├── src/main/java/huynh/tdt/clinicbookingsystem/
│   ├── controller/
│   │   ├── DoctorAppointmentController.java      [NEW]
│   │   ├── AppointmentController.java
│   │   └── ...
│   ├── service/
│   │   ├── AppointmentService.java               [USED]
│   │   ├── DoctorService.java                    [MODIFIED]
│   │   └── ...
│   └── ...
├── src/main/resources/templates/
│   ├── doctor-appointments.html                  [NEW]
│   ├── dashboard.html                            [MODIFIED]
│   └── ...
├── DOCTOR_APPOINTMENTS_FEATURE.md                [NEW]
├── DOCTOR_APPOINTMENTS_QUICKSTART.md             [NEW]
├── IMPLEMENTATION_SUMMARY_*.md                   [NEW]
└── ...
```

---

**Visual Documentation Complete** ✅  
These diagrams help visualize the doctor appointment management feature architecture, data flow, and integration points.

