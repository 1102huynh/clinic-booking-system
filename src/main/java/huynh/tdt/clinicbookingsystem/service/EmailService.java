package huynh.tdt.clinicbookingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired(required = false)
    private JavaMailSender mailSender;

    private static final String FROM_EMAIL = "noreply@clinic-booking-system.com";
    private static final String CLINIC_NAME = "Clinic Booking System";

    /**
     * Send appointment booking confirmation email
     */
    public void sendAppointmentBookingConfirmation(String toEmail, String patientName, String doctorName, String appointmentDate, String appointmentTime) {
        try {
            if (mailSender == null) {
                logger.warn("Email service not configured. Skipping email for: {}", toEmail);
                return;
            }

            String subject = CLINIC_NAME + " - Appointment Booked";
            String body = String.format(
                "Dear %s,\n\n" +
                "Your appointment has been successfully booked with Dr. %s.\n\n" +
                "Appointment Details:\n" +
                "Date: %s\n" +
                "Time: %s\n\n" +
                "Please arrive 10 minutes early.\n\n" +
                "If you need to reschedule or cancel, please log in to your account.\n\n" +
                "Best regards,\n" +
                CLINIC_NAME,
                patientName, doctorName, appointmentDate, appointmentTime
            );

            sendEmail(toEmail, subject, body);
        } catch (Exception e) {
            logger.error("Failed to send booking confirmation email to: {}", toEmail, e);
        }
    }

    /**
     * Send appointment confirmation email (doctor confirmed)
     */
    public void sendAppointmentConfirmedEmail(String toEmail, String patientName, String doctorName, String appointmentDate, String appointmentTime) {
        try {
            if (mailSender == null) {
                logger.warn("Email service not configured. Skipping email for: {}", toEmail);
                return;
            }

            String subject = CLINIC_NAME + " - Appointment Confirmed";
            String body = String.format(
                "Dear %s,\n\n" +
                "Great news! Dr. %s has confirmed your appointment.\n\n" +
                "Appointment Details:\n" +
                "Date: %s\n" +
                "Time: %s\n" +
                "Doctor: Dr. %s\n\n" +
                "Please arrive 10 minutes early.\n\n" +
                "Best regards,\n" +
                CLINIC_NAME,
                patientName, doctorName, appointmentDate, appointmentTime, doctorName
            );

            sendEmail(toEmail, subject, body);
        } catch (Exception e) {
            logger.error("Failed to send confirmation email to: {}", toEmail, e);
        }
    }

    /**
     * Send appointment cancellation email
     */
    public void sendAppointmentCancelledEmail(String toEmail, String patientName, String doctorName, String appointmentDate, String appointmentTime) {
        try {
            if (mailSender == null) {
                logger.warn("Email service not configured. Skipping email for: {}", toEmail);
                return;
            }

            String subject = CLINIC_NAME + " - Appointment Cancelled";
            String body = String.format(
                "Dear %s,\n\n" +
                "Your appointment with Dr. %s has been cancelled.\n\n" +
                "Cancelled Appointment Details:\n" +
                "Date: %s\n" +
                "Time: %s\n" +
                "Doctor: Dr. %s\n\n" +
                "To book another appointment, please log in to your account.\n\n" +
                "If you have questions, please contact us.\n\n" +
                "Best regards,\n" +
                CLINIC_NAME,
                patientName, doctorName, appointmentDate, appointmentTime, doctorName
            );

            sendEmail(toEmail, subject, body);
        } catch (Exception e) {
            logger.error("Failed to send cancellation email to: {}", toEmail, e);
        }
    }

    /**
     * Send appointment completion email
     */
    public void sendAppointmentCompletedEmail(String toEmail, String patientName, String doctorName, String appointmentDate) {
        try {
            if (mailSender == null) {
                logger.warn("Email service not configured. Skipping email for: {}", toEmail);
                return;
            }

            String subject = CLINIC_NAME + " - Appointment Completed - Please Rate";
            String body = String.format(
                "Dear %s,\n\n" +
                "Your appointment with Dr. %s on %s has been completed.\n\n" +
                "Thank you for choosing our clinic!\n\n" +
                "Log in to your account to rate your experience with Dr. %s.\n\n" +
                "Your feedback helps us improve our services.\n\n" +
                "Best regards,\n" +
                CLINIC_NAME,
                patientName, doctorName, appointmentDate, doctorName
            );

            sendEmail(toEmail, subject, body);
        } catch (Exception e) {
            logger.error("Failed to send completion email to: {}", toEmail, e);
        }
    }

    /**
     * Send appointment rejection email
     */
    public void sendAppointmentRejectedEmail(String toEmail, String patientName, String doctorName, String appointmentDate, String appointmentTime) {
        try {
            if (mailSender == null) {
                logger.warn("Email service not configured. Skipping email for: {}", toEmail);
                return;
            }

            String subject = CLINIC_NAME + " - Appointment Not Confirmed";
            String body = String.format(
                "Dear %s,\n\n" +
                "Unfortunately, Dr. %s is unable to confirm your appointment scheduled for %s at %s.\n\n" +
                "Please log in to your account to book another appointment with an available doctor.\n\n" +
                "If you have questions, please contact us.\n\n" +
                "Best regards,\n" +
                CLINIC_NAME,
                patientName, doctorName, appointmentDate, appointmentTime
            );

            sendEmail(toEmail, subject, body);
        } catch (Exception e) {
            logger.error("Failed to send rejection email to: {}", toEmail, e);
        }
    }

    /**
     * Generic email sending method
     */
    private void sendEmail(String to, String subject, String text) {
        if (mailSender == null) {
            logger.warn("Email service not configured. Would send to: {} with subject: {}", to, subject);
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(FROM_EMAIL);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);
            logger.info("Email sent successfully to: {}", to);
        } catch (Exception e) {
            logger.error("Error sending email to: {}", to, e);
        }
    }
}

