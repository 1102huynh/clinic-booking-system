-- ===========================================================
-- Complete Database Reset and Setup Script
-- Run this ENTIRE script to reset your database completely
-- ===========================================================

-- Drop and recreate the database
DROP DATABASE IF EXISTS clinic_db;
CREATE DATABASE clinic_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE clinic_db;

-- ===========================================================
-- USERS TABLE
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
-- USER_ROLES TABLE
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
    user_id BIGINT NOT NULL,
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
    user_id BIGINT NOT NULL,
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
-- INSERT ROLES
-- ===========================================================
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_DOCTOR');
INSERT INTO roles (name) VALUES ('ROLE_PATIENT');

-- ===========================================================
-- INSERT SAMPLE USERS WITH BCRYPT PASSWORDS
-- ===========================================================
-- Passwords BCrypt encoded:
-- admin123 -> $2a$10$slYQmyNdGzin7olVN3p5aOWDff8rIK4nVu.OtQQNYTpd0/MV2yJwm
-- password123 -> $2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2

INSERT INTO users (id, username, password, full_name, email, role, enabled)
VALUES
    (1, 'admin', '$2a$10$slYQmyNdGzin7olVN3p5aOWDff8rIK4nVu.OtQQNYTpd0/MV2yJwm', 'System Admin', 'admin@clinic.com', 'ADMIN', TRUE),
    (2, 'drsmith', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. John Smith', 'drsmith@clinic.com', 'DOCTOR', TRUE),
    (3, 'patient01', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Alice Nguyen', 'alice@example.com', 'PATIENT', TRUE);

-- ===========================================================
-- INSERT USER ROLES MAPPING
-- ===========================================================
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1); -- admin -> ROLE_ADMIN
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2); -- drsmith -> ROLE_DOCTOR
INSERT INTO user_roles (user_id, role_id) VALUES (3, 3); -- patient01 -> ROLE_PATIENT

-- ===========================================================
-- INSERT DOCTORS DATA
-- ===========================================================
INSERT INTO doctors (user_id, specialization, experience_years, available)
VALUES (2, 'Cardiology', 5, TRUE);

-- ===========================================================
-- INSERT PATIENTS DATA
-- ===========================================================
INSERT INTO patients (user_id, date_of_birth, gender, phone, address)
VALUES (3, '1995-04-12', 'FEMALE', '0988888888', 'Ho Chi Minh City');

-- ===========================================================
-- INSERT SAMPLE APPOINTMENT
-- ===========================================================
INSERT INTO appointments (patient_id, doctor_id, appointment_time, status, notes)
VALUES (1, 1, '2025-10-26 09:00:00', 'PENDING', 'Heart checkup');

-- ===========================================================
-- VERIFICATION QUERIES
-- ===========================================================
SELECT '=== USERS ===' AS section;
SELECT id, username, enabled, role FROM users;

SELECT '=== USER ROLES ===' AS section;
SELECT u.username, r.name FROM users u
LEFT JOIN user_roles ur ON u.id = ur.user_id
LEFT JOIN roles r ON ur.role_id = r.id;

SELECT '=== PASSWORD VERIFICATION ===' AS section;
SELECT username, SUBSTRING(password, 1, 30) as password_start FROM users;

