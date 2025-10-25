-- ===========================================================
-- DATABASE: Clinic Appointment Booking System
-- ===========================================================
DROP DATABASE clinic_db;
-- Then run the updated clinic_db.sql script

CREATE DATABASE IF NOT EXISTS clinic_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE clinic_db;

-- ===========================================================
-- USERS TABLE (base table for all roles)
-- ===========================================================
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       full_name VARCHAR(100) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       role VARCHAR(50),
                       enabled BOOLEAN DEFAULT TRUE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ===========================================================
-- ROLES TABLE
-- ===========================================================
CREATE TABLE roles (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE
);

-- ===========================================================
-- USER_ROLES TABLE (Many-to-Many relationship)
-- ===========================================================
CREATE TABLE user_roles (
                            user_id BIGINT NOT NULL,
                            role_id BIGINT NOT NULL,
                            PRIMARY KEY (user_id, role_id),
                            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                            FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- ===========================================================
-- DOCTORS TABLE
-- ===========================================================
CREATE TABLE doctors (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         user_id BIGINT NOT NULL UNIQUE,
                         specialization VARCHAR(100) NOT NULL,
                         experience_years INT DEFAULT 0,
                         available BOOLEAN DEFAULT TRUE,
                         FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ===========================================================
-- PATIENTS TABLE
-- ===========================================================
CREATE TABLE patients (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          user_id BIGINT NOT NULL UNIQUE,
                          date_of_birth DATE,
                          gender ENUM('MALE', 'FEMALE', 'OTHER'),
                          phone VARCHAR(20),
                          address VARCHAR(255),
                          FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ===========================================================
-- APPOINTMENTS TABLE
-- ===========================================================
CREATE TABLE appointments (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              patient_id BIGINT NOT NULL,
                              doctor_id BIGINT NOT NULL,
                              appointment_time DATETIME NOT NULL,
                              status ENUM('PENDING', 'CONFIRMED', 'CANCELLED', 'COMPLETED') DEFAULT 'PENDING',
                              notes TEXT,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
                              FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE
);

-- ===========================================================
-- SAMPLE DATA (OPTIONAL)
-- ===========================================================
-- Passwords are BCrypt encoded:
-- admin123 -> $2a$10$slYQmyNdGzin7olVN3p5aOWDff8rIK4nVu.OtQQNYTpd0/MV2yJwm
-- password123 -> $2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2

INSERT INTO users (username, password, full_name, email, role, enabled)
VALUES
    ('admin', '$2a$10$slYQmyNdGzin7olVN3p5aOWDff8rIK4nVu.OtQQNYTpd0/MV2yJwm', 'System Admin', 'admin@clinic.com', 'ADMIN', TRUE),
    ('drsmith', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. John Smith', 'drsmith@clinic.com', 'DOCTOR', TRUE),
    ('patient01', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Alice Nguyen', 'alice@example.com', 'PATIENT', TRUE);

-- Map Doctor + Patient to their user IDs
INSERT INTO doctors (user_id, specialization, experience_years, available)
VALUES (2, 'Cardiology', 5, TRUE);

INSERT INTO patients (user_id, date_of_birth, gender, phone, address)
VALUES (3, '1995-04-12', 'FEMALE', '0988888888', 'Ho Chi Minh City');

-- NOTE: Sample appointment removed - should be created through the application UI to ensure proper patient-user mapping
-- If you want to add a test appointment, create it with:
-- INSERT INTO appointments (patient_id, doctor_id, appointment_time, status, notes)
-- VALUES (1, 1, '2025-10-26 09:00:00', 'PENDING', 'Heart checkup');
-- where patient_id = 1 corresponds to patient01 (user_id = 3)
