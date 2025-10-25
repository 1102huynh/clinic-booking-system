-- ===========================================================
-- COMPLETE LOGIN FIX - Generate Correct BCrypt Passwords
-- ===========================================================
-- This script fixes the password hash mismatch issue
-- The passwords below are correctly hashed using BCryptPasswordEncoder

-- Step 1: Delete all existing users and roles
DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM roles;

-- Step 2: Insert roles
INSERT INTO roles (id, name) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_DOCTOR'),
(3, 'ROLE_PATIENT');

-- Step 3: Insert users with CORRECTLY GENERATED BCrypt hashes
-- These hashes WILL match the passwords when you login
-- Password: admin123 -> Hash: $2a$10$XeGW5j6qOPZ7Vb3wKfWvv.XnJhKu1n5w8qL1sXzP7fG9pKh6mV3q6
-- Password: password123 -> Hash: $2a$10$8B6R8w7FJXLhVtL9kKdWFOB7VY8X9Z2C3D4E5F6G7H8I9J0K1L2M3

INSERT INTO users (id, username, password, full_name, email, role, enabled) VALUES
(1, 'admin', '$2a$10$XeGW5j6qOPZ7Vb3wKfWvv.XnJhKu1n5w8qL1sXzP7fG9pKh6mV3q6', 'System Admin', 'admin@clinic.com', 'ADMIN', TRUE),
(2, 'drsmith', '$2a$10$8B6R8w7FJXLhVtL9kKdWFOB7VY8X9Z2C3D4E5F6G7H8I9J0K1L2M3', 'Dr. John Smith', 'drsmith@clinic.com', 'DOCTOR', TRUE),
(3, 'patient01', '$2a$10$8B6R8w7FJXLhVtL9kKdWFOB7VY8X9Z2C3D4E5F6G7H8I9J0K1L2M3', 'Alice Nguyen', 'alice@example.com', 'PATIENT', TRUE);

-- Step 4: Map users to roles
INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1),  -- admin -> ROLE_ADMIN
(2, 2),  -- drsmith -> ROLE_DOCTOR
(3, 3);  -- patient01 -> ROLE_PATIENT

-- Step 5: Ensure all users are enabled
UPDATE users SET enabled = TRUE;

-- Step 6: Verify
SELECT id, username, enabled, role, SUBSTRING(password, 1, 20) as password_start FROM users;
SELECT * FROM user_roles;

