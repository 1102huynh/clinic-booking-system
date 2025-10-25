-- Ensure all necessary tables exist for the appointment booking feature

USE clinic_db;

-- Add missing columns if they don't exist
ALTER TABLE appointments
ADD COLUMN IF NOT EXISTS appointment_date DATE,
ADD COLUMN IF NOT EXISTS appointment_time_only TIME;

-- Create indexes for better query performance
CREATE INDEX IF NOT EXISTS idx_appointments_patient_id ON appointments(patient_id);
CREATE INDEX IF NOT EXISTS idx_appointments_doctor_id ON appointments(doctor_id);
CREATE INDEX IF NOT EXISTS idx_appointments_status ON appointments(status);
CREATE INDEX IF NOT EXISTS idx_appointments_time ON appointments(appointment_time);
CREATE INDEX IF NOT EXISTS idx_doctors_user_id ON doctors(user_id);
CREATE INDEX IF NOT EXISTS idx_doctors_available ON doctors(available);
CREATE INDEX IF NOT EXISTS idx_doctors_specialization ON doctors(specialization);
CREATE INDEX IF NOT EXISTS idx_patients_user_id ON patients(user_id);
CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);

-- Verify sample data exists
SELECT COUNT(*) as doctor_count FROM doctors;
SELECT COUNT(*) as patient_count FROM patients;
SELECT COUNT(*) as appointment_count FROM appointments;

