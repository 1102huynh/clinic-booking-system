# /admin/appointments - Visual Reference Guide

## 🎯 Quick Visual Guide

### Navigation Map

```
┌─────────────────────────────────────────────────────────────┐
│                    CLINIC DASHBOARD                         │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  Admin Dashboard                                            │
│  ├─ 📊 View Patients                                        │
│  ├─ 👥 Manage Users                                         │
│  ├─ 📅 Manage Appointments ◄─── You are here               │
│  └─ 📈 View Statistics                                      │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │         📅 MANAGE APPOINTMENTS PAGE                 │   │
│  ├─────────────────────────────────────────────────────┤   │
│  │                                                     │   │
│  │  List all appointments                              │   │
│  │  ┌─────────────────────────────────────────────┐   │   │
│  │  │ Patient │ Doctor │ Time     │ Status │ View │   │   │
│  │  ├─────────────────────────────────────────────┤   │   │
│  │  │ John    │ Smith  │ 10/30    │ ⏳    │ [V] │──┐ │   │
│  │  │ Jane    │ Jones  │ 10/31    │ ✓     │ [V] │  │ │   │
│  │  │ Bob     │ Brown  │ 11/01    │ ✗     │ [V] │  │ │   │
│  │  └─────────────────────────────────────────────┘   │   │
│  │                                                     │   │
│  │                        Detail Page                  │   │
│  │                        ↓ ↓ ↓                        │   │
│  │  ┌─────────────────────────────────────────────┐   │   │
│  │  │  Appointment #1001          [Status: ✓]    │   │   │
│  │  ├─────────────────────────────────────────────┤   │   │
│  │  │                                             │   │   │
│  │  │  Patient: John Doe                          │   │   │
│  │  │  Doctor: Dr. Smith                          │   │   │
│  │  │  Time: 10/30/2025 14:30                     │   │   │
│  │  │  Status: CONFIRMED ✓                        │   │   │
│  │  │                                             │   │   │
│  │  │  [Back]  [Confirm]  [Cancel]                │   │   │
│  │  └─────────────────────────────────────────────┘   │   │
│  │                                                     │   │
│  └─────────────────────────────────────────────────────┘   │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Page Flow Diagram

```
START
│
├─ Login
│  └─ Admin Dashboard
│     │
│     ├─ Click "Manage Appointments"
│     │  │
│     │  └─ GET /admin/appointments
│     │     │
│     │     ├─ Render appointments list
│     │     │  │
│     │     │  └─ Display table of all appointments
│     │     │     │
│     │     │     ├─ PENDING (yellow)
│     │     │     ├─ CONFIRMED (green)
│     │     │     ├─ COMPLETED (blue)
│     │     │     └─ CANCELLED (red)
│     │     │
│     │     └─ Click "View" on appointment
│     │        │
│     │        └─ GET /admin/appointments/{id}
│     │           │
│     │           ├─ Render appointment detail
│     │           │  │
│     │           │  ├─ Patient Information ✓
│     │           │  ├─ Doctor Information ✓
│     │           │  ├─ Appointment Details ✓
│     │           │  └─ Action Buttons:
│     │           │     │
│     │           │     ├─ IF Status = PENDING
│     │           │     │  └─ [✓ Confirm Appointment]
│     │           │     │     │
│     │           │     │     └─ POST /admin/appointments/{id}/confirm
│     │           │     │        ├─ Update Status → CONFIRMED
│     │           │     │        ├─ Send Confirmation Email
│     │           │     │        └─ Redirect to detail page
│     │           │     │
│     │           │     └─ IF Status != COMPLETED
│     │           │        └─ [✗ Cancel Appointment]
│     │           │           │
│     │           │           ├─ Show Confirmation Dialog
│     │           │           │  ("Are you sure?")
│     │           │           │
│     │           │           └─ POST /admin/appointments/{id}/cancel
│     │           │              ├─ Update Status → CANCELLED
│     │           │              ├─ Send Cancellation Email
│     │           │              └─ Redirect to detail page
│     │           │
│     │           └─ [← Back to List]
│     │              └─ Return to appointments list
│     │
│     └─ Back to Dashboard (continue admin tasks)
│
END
```

### Status Color Reference

```
┌─────────────────┬──────────────┬───────────┬──────────────┐
│     STATUS      │    COLOR     │  HEX      │   MEANING    │
├─────────────────┼──────────────┼───────────┼──────────────┤
│    PENDING      │    YELLOW    │ #fff3cd   │ Awaiting     │
│                 │              │           │ confirmation │
├─────────────────┼──────────────┼───────────┼──────────────┤
│   CONFIRMED     │    GREEN     │ #d4edda   │ Confirmed    │
│                 │              │           │ appointment  │
├─────────────────┼──────────────┼───────────┼──────────────┤
│   COMPLETED     │     BLUE     │ #d1ecf1   │ Visit done   │
│                 │              │           │ (terminal)   │
├─────────────────┼──────────────┼───────────┼──────────────┤
│   CANCELLED     │      RED     │ #f8d7da   │ Cancelled    │
│                 │              │           │ (terminal)   │
└─────────────────┴──────────────┴───────────┴──────────────┘
```

### Button Availability Matrix

```
┌──────────────┬─────────────┬──────────────┬──────────────┐
│    STATUS    │  CONFIRM?   │   CANCEL?    │     NOTES    │
├──────────────┼─────────────┼──────────────┼──────────────┤
│  PENDING     │     ✓ YES   │     ✓ YES    │ Both available│
├──────────────┼─────────────┼──────────────┼──────────────┤
│  CONFIRMED   │     ✗ NO    │     ✓ YES    │ Can cancel   │
├──────────────┼─────────────┼──────────────┼──────────────┤
│  COMPLETED   │     ✗ NO    │     ✗ NO     │ No actions   │
├──────────────┼─────────────┼──────────────┼──────────────┤
│  CANCELLED   │     ✗ NO    │     ✗ NO     │ No actions   │
└──────────────┴─────────────┴──────────────┴──────────────┘
```

### API Endpoint Map

```
GET /admin/appointments
└─ Retrieve all appointments
   ├─ Sorted: by appointmentTime DESC
   ├─ Format: HTML table view
   └─ Returns: List of appointment records

GET /admin/appointments/{appointmentId}
└─ Retrieve specific appointment details
   ├─ Includes: Patient info, Doctor info
   ├─ Format: HTML detail view
   └─ Returns: Single appointment with related data

POST /admin/appointments/{appointmentId}/confirm
└─ Confirm a pending appointment
   ├─ Status: PENDING → CONFIRMED
   ├─ Action: Send confirmation email
   ├─ Format: Form submission (POST)
   └─ Response: Redirect to detail page

POST /admin/appointments/{appointmentId}/cancel
└─ Cancel an appointment
   ├─ Status: → CANCELLED (if allowed)
   ├─ Action: Send cancellation email
   ├─ Format: Form submission (POST)
   └─ Response: Redirect to detail page
```

### Data Structure

```
APPOINTMENT ENTITY
├─ id: Long
├─ patient: Patient
│  ├─ id: Long
│  ├─ user: User
│  │  ├─ fullName: String
│  │  ├─ email: String
│  │  └─ phone: String
│  └─ dateOfBirth: LocalDate
├─ doctor: Doctor
│  ├─ id: Long
│  ├─ specialization: String
│  └─ user: User
│     ├─ fullName: String
│     ├─ email: String
│     └─ phone: String
├─ appointmentTime: LocalDateTime
├─ status: AppointmentStatus {PENDING, CONFIRMED, COMPLETED, CANCELLED}
├─ notes: String
└─ createdAt: LocalDateTime
```

### Email Flow

```
CONFIRMATION EMAIL FLOW:
┌─────────────┐
│   PENDING   │
└──────┬──────┘
       │
       ├─ Admin clicks "Confirm"
       │
       ├─ POST to /confirm endpoint
       │
       ├─ Status updated: PENDING → CONFIRMED
       │
       ├─ EmailService triggered
       │  ├─ Get patient email
       │  ├─ Get patient name
       │  ├─ Get doctor name
       │  └─ Send email
       │
       └─ Response: Success message

CANCELLATION EMAIL FLOW:
┌──────────────┐
│ PENDING or   │
│ CONFIRMED    │
└──────┬───────┘
       │
       ├─ Admin clicks "Cancel"
       │
       ├─ Confirmation dialog
       │
       ├─ POST to /cancel endpoint
       │
       ├─ Status updated: → CANCELLED
       │
       ├─ EmailService triggered
       │  ├─ Get patient email
       │  ├─ Get patient name
       │  ├─ Get doctor name
       │  └─ Send email
       │
       └─ Response: Success message
```

### File Structure Tree

```
clinic-booking-system/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── huynh/tdt/clinicbookingsystem/
│       │       └── controller/
│       │           └── AdminDashboardController.java ✏️ MODIFIED
│       │
│       └── resources/
│           └── templates/
│               ├── admin-appointments.html ✨ NEW
│               ├── admin-appointment-detail.html ✨ NEW
│               └── (other existing templates)
│
├── ADMIN_APPOINTMENTS_IMPLEMENTATION.md ✨ NEW
├── ADMIN_APPOINTMENTS_QUICK_REFERENCE.md ✨ NEW
├── ADMIN_APPOINTMENTS_USER_GUIDE.md ✨ NEW
├── IMPLEMENTATION_COMPLETE.md ✨ NEW
├── VERIFICATION_CHECKLIST.md ✨ NEW
│
└── (other project files)
```

### Security Model

```
REQUEST FLOW:
┌─ Client Request
├─ Spring Security Filter
│  └─ Is user authenticated?
│     ├─ No → Redirect to login
│     └─ Yes → Continue
├─ Authorization Check
│  └─ Does user have ADMIN role?
│     ├─ No → 403 Forbidden
│     └─ Yes → Continue
├─ CSRF Validation (for POST)
│  └─ Is CSRF token valid?
│     ├─ No → 403 Forbidden
│     └─ Yes → Continue
├─ Controller Method
│  └─ Process request
├─ Service Layer
│  └─ Execute business logic
├─ Database Layer
│  └─ Query/Update data
└─ Response
   └─ HTML or Redirect
```

### Error Handling Flow

```
NORMAL FLOW:
Request
  ↓
Try Block
  ├─ Fetch data from DB
  ├─ Process data
  ├─ Render template
  └─ Return response

ERROR FLOW:
Request
  ↓
Try Block
  └─ Exception occurs
     ↓
     Catch Block
       ├─ Log error
       ├─ Create error message
       ├─ Add to RedirectAttributes
       └─ Redirect to same page
         ↓
         Display error alert to user
```

### Responsive Design Breakpoints

```
DESKTOP (≥1024px)
┌─────────────────────────────────────────┐
│  Navbar with full spacing                │
├─────────────────────────────────────────┤
│  Table with all columns visible          │
│  ┌─────────────────��───────────────────┐ │
│  │ Col │ Col │ Col │ Col │ Col │ Col │ │
│  └─────────────────────────────────────┘ │
└─────────────────────────────────────────┘

TABLET (768px - 1023px)
┌────────────────────────────┐
│  Navbar condensed           │
├────────────────────────────┤
│  Table columns wrap         │
│  ┌────────────���───────────┐ │
│  │ Column │ Column        │ │
│  │ Value  │ Value         │ │
│  └────────────────────────┘ │
└────────────────────────────┘

MOBILE (<768px)
┌────────────┐
│ Nav ≡      │
├────────────┤
│ Stack      │
│ Layout     │
│ ┌────────┐ │
│ │ Data   │ │
│ │ Stack  │ │
│ └────────┘ │
└────────────┘
```

### Performance Metrics

```
TARGET PERFORMANCE:
┌─ Page Load Time
│  └─ < 2 seconds (for list and detail pages)
│
├─ Action Response Time
│  └─ < 1 second (for confirm/cancel)
│
├─ Database Query Time
│  └─ < 500ms (for all queries)
│
├─ Email Send Time
│  └─ < 2 seconds (non-blocking)
│
└─ Memory Usage
   └─ < 500MB heap (for typical load)
```

### Testing Scenarios

```
SCENARIO 1: Confirm Appointment
┌─ Navigate to appointment (PENDING status)
├─ Click "✓ Confirm Appointment"
├─ Status changes to CONFIRMED
├─ Email sent to patient
└─ Success message displayed

SCENARIO 2: Cancel Appointment
┌─ Navigate to appointment (non-terminal status)
├─ Click "✗ Cancel Appointment"
├─ Confirm in dialog
├─ Status changes to CANCELLED
├─ Email sent to patient
└─ Success message displayed

SCENARIO 3: Invalid State Transition
┌─ Navigate to COMPLETED appointment
├─ No action buttons visible
└─ Message: "No actions available"

SCENARIO 4: Invalid Appointment ID
┌─ Navigate to /admin/appointments/99999
├─ Page loads with error
└─ Message: "Appointment not found"
```

### Integration Points

```
┌─ AdminDashboardController
│  ├─ Depends on: AppointmentService
│  ├─ Uses: AppointmentRepository
│  ├─ Uses: Spring Security
│  └─ Renders: Thymeleaf templates
│
├─ AppointmentService
│  ├─ Depends on: AppointmentRepository
│  ├─ Depends on: EmailService
│  ├─ Depends on: PatientRepository
│  └─ Depends on: DoctorRepository
│
├─ EmailService
│  └─ Sends: SMTP emails
│
└─ Templates
   ├─ Uses: Thymeleaf expressions
   ├─ Uses: CSS styling
   └─ Uses: JavaScript (confirmations)
```

### Summary Card

```
╔══════════════════════════════════════════════════════════╗
║         /admin/appointments Feature Summary             ║
╠══════════════════════════════════════════════════════════╣
║                                                          ║
║  Status: ✅ FULLY IMPLEMENTED                           ║
║  Type: Web Feature (MVC + Templates)                    ║
║  Users: Admin users only                                ║
║  Access Level: Requires ADMIN role                      ║
║                                                          ║
║  Core Functionality:                                    ║
║  ✓ List all clinic appointments                         ║
║  ✓ View appointment details                             ║
║  ✓ Confirm pending appointments                         ║
║  ✓ Cancel eligible appointments                         ║
║  ✓ Send email notifications                             ║
║                                                          ║
║  Files Created: 2 templates + 4 docs                    ║
║  Files Modified: 1 controller                           ║
║  Database Changes: None required                        ║
║  New Dependencies: None required                        ║
║                                                          ║
║  Security: ✓ Authenticated ✓ Authorized                ║
║  Error Handling: ✓ Comprehensive                        ║
║  Testing: ⏳ Ready for QA                               ║
║  Deployment: ⏳ Ready to deploy                         ║
║                                                          ║
╚══════════════════════════════════════════════════════════╝
```

---

**Version:** 1.0  
**Date:** October 27, 2025  
**Status:** ✅ Complete

