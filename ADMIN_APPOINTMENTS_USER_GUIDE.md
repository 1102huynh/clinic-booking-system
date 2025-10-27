# Admin Appointments - User Guide & Examples

## Feature Overview

The Admin Appointments feature provides a comprehensive interface for managing all clinic appointments. Administrators can view, confirm, and cancel appointments while automatic email notifications keep patients informed.

## User Interface

### Dashboard Layout

```
┌─────────────────────────────────────────────────────────┐
│ 🏥 Appointment Management              [Logout]         │
├─────────────────────────────────────────────────────────┤
│                                                         │
│ ← Back to Admin Dashboard                              │
│                                                         │
│ 📅 Manage Appointments                                  │
│                                                         │
│ ┌─────────────────────────────────────────────────────┐ │
│ │ Patient Name  │ Doctor   │ Special. │ Date   │Status│ │
│ ├─────────────────────────────────────────────────────┤ │
│ │ John Doe      │ Dr. Smith│ Cardio   │10/30   │✓ OK  │ │
│ │ Jane Smith    │ Dr. Jones│ Neuro    │10/31   │⏳ PND │ │
│ │ Bob Wilson    │ Dr. Brown│ Ortho    │11/01   │✗ CAN │ │
│ └─────────────────────────────────────────────────────┘ │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

### Appointment Detail Layout

```
┌─────────────────────────────────────────────────────────┐
│ 📋 Appointment Details                 [Logout]         │
├─────────────────────────────────────────────────────────┤
│                                                         │
│ ← Back to Appointments                                  │
│                                                         │
│ Appointment #123                        [CONFIRMED ✓]  │
│                                                         │
│ 👤 PATIENT INFORMATION                                  │
│ ├─ Patient Name: John Doe                              │
│ ├─ Email: john@example.com                             │
│ ├─ Phone: (555) 123-4567                               │
│ └─ DOB: 01/15/1990                                      │
│                                                         │
│ 👨‍⚕️ DOCTOR INFORMATION                                   │
│ ├─ Doctor: Dr. Sarah Smith                             │
│ ├─ Specialization: Cardiology                          │
│ ├─ Email: sarah@clinic.com                             │
│ └─ Phone: (555) 987-6543                               │
│                                                         │
│ 📅 APPOINTMENT DETAILS                                  │
│ ├─ Date & Time: 30/10/2025 14:30                        │
│ ├─ Status: CONFIRMED                                    │
│ └─ Created: 25/10/2025 10:15                            │
│                                                         │
│ 📝 NOTES                                                │
│ └─ Patient has history of hypertension                 │
│                                                         │
│ [Back to List]  [Cancel Appointment]                   │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

## User Workflows

### Workflow 1: Viewing All Appointments

```
┌─ Admin Dashboard
│
└─ Click "Manage Appointments"
   │
   └─ GET /admin/appointments
      │
      ├─ Load all appointments from database
      ├─ Sort by appointment_time DESC
      ├─ Render admin-appointments.html
      │
      └─ Display: Table with columns
         ├─ Patient Name
         ├─ Doctor Name
         ├─ Specialization
         ├─ Date & Time
         ├─ Status (colored badge)
         └─ Action (View button)
```

### Workflow 2: Viewing Appointment Details

```
┌─ Appointments List
│
└─ Click "View" button for appointment
   │
   └─ GET /admin/appointments/{appointmentId}
      │
      ├─ Fetch appointment from database
      ├─ Load patient details
      ├─ Load doctor details
      ├─ Render admin-appointment-detail.html
      │
      └─ Display: Detailed view with
         ├─ Patient information
         ├─ Doctor information
         ├─ Appointment details
         ├─ Notes
         └─ Action buttons
```

### Workflow 3: Confirming an Appointment

```
┌─ Appointment Detail (Status: PENDING)
│
├─ "✓ Confirm Appointment" button visible
│
└─ Click button
   │
   ├─ POST /admin/appointments/{appointmentId}/confirm
   │  │
   │  ├─ AdminDashboardController.confirmAppointment()
   │  │  │
   │  │  └─ AppointmentService.confirmAppointment(appointmentId)
   │  │     │
   │  │     ├─ Load appointment (validate exists)
   │  │     ├─ Check status is PENDING
   │  │     ├─ Update status → CONFIRMED
   │  │     ├─ Save to database
   │  │     │
   │  │     └─ EmailService.sendAppointmentConfirmedEmail()
   │  │        ├─ Get patient email
   │  │        ├─ Get patient name
   │  │        ├─ Get doctor name
   │  │        └─ Send email
   │  │
   │  └─ RedirectAttributes.addFlashAttribute("success", "...")
   │
   └─ Redirect to /admin/appointments/{appointmentId}
      │
      └─ Display: Detail page with
         ├─ Status: CONFIRMED ✓
         ├─ Success message
         └─ Cancel button (now available)
```

### Workflow 4: Cancelling an Appointment

```
┌─ Appointment Detail (Status: PENDING or CONFIRMED)
│
├─ "✗ Cancel Appointment" button visible
│
└─ Click button
   │
   ├─ Browser shows confirmation dialog:
   │  "Are you sure you want to cancel this appointment?"
   │
   └─ If confirmed:
      │
      ├─ POST /admin/appointments/{appointmentId}/cancel
      │  │
      │  ├─ AdminDashboardController.cancelAppointment()
      │  │  │
      │  │  └─ AppointmentService.cancelAppointment(appointmentId)
      │  │     │
      │  │     ├─ Load appointment (validate exists)
      │  │     ├─ Check status != CANCELLED and != COMPLETED
      │  │     ├─ Update status → CANCELLED
      │  │     ├─ Save to database
      │  │     │
      │  │     └─ EmailService.sendAppointmentCancelledEmail()
      │  │        ├─ Get patient email
      │  │        ├─ Get patient name
      │  │        ├─ Get doctor name
      │  │        └─ Send email
      │  │
      │  └─ RedirectAttributes.addFlashAttribute("success", "...")
      │
      └─ Redirect to /admin/appointments/{appointmentId}
         │
         └─ Display: Detail page with
            ├─ Status: CANCELLED ✗
            ├─ Success message
            └─ Cancel button (now disabled)
```

## Status Transitions

### Valid State Transitions

```
         ┌──────────────────┐
         │     PENDING      │
         └────────┬─────────┘
                  │
          ┌───────┼───────┐
          │               │
          v               v
      CONFIRMED       CANCELLED
          │
          └────────────────┐
                           │
                           v
                      COMPLETED
                      (terminal)
```

### Transition Rules

| From → To | Allowed | By | Method |
|-----------|---------|----|----|
| PENDING → CONFIRMED | ✅ | Admin | `/confirm` |
| PENDING → CANCELLED | ✅ | Admin | `/cancel` |
| CONFIRMED → CANCELLED | ✅ | Admin | `/cancel` |
| CONFIRMED → COMPLETED | ❌ | - | System only |
| COMPLETED → * | ❌ | - | Terminal state |
| CANCELLED → * | ❌ | - | Terminal state |

## Email Notifications

### Confirmation Email

**Subject:** Appointment Confirmation - Clinic Booking System

**Body:**
```
Dear John Doe,

Your appointment has been confirmed.

Appointment Details:
- Doctor: Dr. Sarah Smith (Cardiology)
- Date: 30/10/2025
- Time: 14:30

Please arrive 10 minutes early.

Best regards,
Clinic Booking System
```

### Cancellation Email

**Subject:** Appointment Cancellation - Clinic Booking System

**Body:**
```
Dear John Doe,

Your appointment has been cancelled.

Cancelled Appointment:
- Doctor: Dr. Sarah Smith (Cardiology)
- Date: 30/10/2025
- Time: 14:30

If you have questions, please contact us.

Best regards,
Clinic Booking System
```

## Error Scenarios

### Scenario 1: Trying to Confirm Already Confirmed

```
┌─ User navigates to CONFIRMED appointment
│
└─ "✓ Confirm Appointment" button is NOT visible
   │
   └─ Only "← Back to List" and "✗ Cancel" buttons show
```

### Scenario 2: Trying to Cancel Completed

```
┌─ User navigates to COMPLETED appointment
│
└─ "✗ Cancel Appointment" button is NOT visible
   │
   └─ Only "← Back to List" button shows
```

### Scenario 3: Invalid Appointment ID

```
┌─ GET /admin/appointments/99999 (non-existent)
│
└─ Server returns to detail page with:
   ├─ Error alert: "Failed to load appointment: Appointment not found"
   └─ Empty state: "Appointment not found"
```

### Scenario 4: Database Connection Error

```
┌─ POST /admin/appointments/{id}/confirm
│
└─ Server catches exception
   │
   └─ Redirect with error:
      "Failed to confirm appointment: [error details]"
```

## Example Data

### Example Appointment #1

```
ID: 1001
Patient: John Doe (john@example.com)
Doctor: Dr. Sarah Smith
Specialization: Cardiology
Date/Time: 30/10/2025 14:30
Status: PENDING
Notes: Patient has history of hypertension
Created: 25/10/2025 10:15 AM
```

**Actions Available:**
- ✓ Confirm Appointment
- ✗ Cancel Appointment

### Example Appointment #2

```
ID: 1002
Patient: Jane Smith (jane@example.com)
Doctor: Dr. Michael Johnson
Specialization: Neurology
Date/Time: 31/10/2025 09:00
Status: CONFIRMED
Notes: Follow-up consultation
Created: 26/10/2025 11:30 AM
```

**Actions Available:**
- ✗ Cancel Appointment
(Note: Confirm button not shown because status is not PENDING)

### Example Appointment #3

```
ID: 1003
Patient: Bob Wilson (bob@example.com)
Doctor: Dr. Lisa Brown
Specialization: Orthopedics
Date/Time: 01/11/2025 15:45
Status: CANCELLED
Notes: Patient requested to reschedule
Created: 27/10/2025 09:00 AM
```

**Actions Available:**
- None (terminal state)

## Response Examples

### List All Appointments - Success

```
HTTP/1.1 200 OK
Content-Type: text/html; charset=UTF-8

<html>
  <!-- admin-appointments.html rendered with data -->
  <table>
    <tr>
      <td>John Doe</td>
      <td>Dr. Smith</td>
      <td>Cardiology</td>
      <td>30/10/2025 14:30</td>
      <td><span class="status-pending">PENDING</span></td>
      <td><a href="/admin/appointments/1001">View</a></td>
    </tr>
  </table>
</html>
```

### View Details - Success

```
HTTP/1.1 200 OK
Content-Type: text/html; charset=UTF-8

<html>
  <!-- admin-appointment-detail.html rendered with data -->
  <h2>Appointment #1001</h2>
  <div class="status-badge status-pending">PENDING</div>
  <h3>Patient Information</h3>
  <p>John Doe</p>
  <p>john@example.com</p>
  ...
</html>
```

### Confirm - Success

```
HTTP/1.1 302 Found
Location: /admin/appointments/1001

Set-Cookie: org.springframework.web.servlet.support.FlashMap...
  success=Appointment confirmed successfully!
```

### Confirm - Error

```
HTTP/1.1 302 Found
Location: /admin/appointments/1001

Set-Cookie: org.springframework.web.servlet.support.FlashMap...
  error=Failed to confirm appointment: Only pending appointments can be confirmed
```

## Testing with cURL

### Get Appointments List
```bash
curl -H "Cookie: JSESSIONID=..." \
  http://localhost:8080/admin/appointments
```

### Get Appointment Details
```bash
curl -H "Cookie: JSESSIONID=..." \
  http://localhost:8080/admin/appointments/1001
```

### Confirm Appointment
```bash
curl -X POST \
  -H "Cookie: JSESSIONID=..." \
  http://localhost:8080/admin/appointments/1001/confirm
```

### Cancel Appointment
```bash
curl -X POST \
  -H "Cookie: JSESSIONID=..." \
  http://localhost:8080/admin/appointments/1001/cancel
```

## Performance Considerations

### Query Optimization
- All appointments are loaded and sorted in memory
- For large datasets, consider pagination
- Index on `appointment_time` for sorting

### Future Improvements
- Add pagination: `?page=1&size=10`
- Add filtering: `?status=PENDING`
- Add search: `?search=john`
- Add date range filter: `?from=2025-10-01&to=2025-10-31`

## Security Considerations

### Authentication
- All endpoints require login
- Session must have ADMIN role
- CSRF tokens validated on POST requests

### Authorization
- Only admins can access `/admin/*` endpoints
- User ID matched against session principal
- No direct SQL injection possible (JPA used)

### Data Protection
- Password fields never exposed
- Emails used for notifications only
- Sensitive data validated on server-side

## Accessibility

- ✅ Semantic HTML structure
- ✅ Color not sole indicator (badges have text)
- ✅ Keyboard navigation supported
- ✅ Form labels associated with inputs
- ✅ Confirmation dialogs for destructive actions

## Browser Compatibility

- ✅ Chrome 90+
- ✅ Firefox 88+
- ✅ Safari 14+
- ✅ Edge 90+
- ✅ Mobile browsers (responsive)

## Conclusion

The Admin Appointments feature provides a robust, user-friendly interface for managing clinic appointments. With automatic email notifications, status validation, and comprehensive error handling, it ensures smooth appointment administration.

