-- ===========================================================
-- Insert Test Users with BCrypt Passwords
-- Run this script to add test users to your database
-- ===========================================================

USE clinic_db;

-- Clear existing users (optional)
-- DELETE FROM user_roles;
-- DELETE FROM users;
-- DELETE FROM roles;

-- Insert roles
INSERT IGNORE INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT IGNORE INTO roles (name) VALUES ('ROLE_DOCTOR');
INSERT IGNORE INTO roles (name) VALUES ('ROLE_PATIENT');

-- Insert test users with BCrypt encoded passwords
-- admin123 -> $2a$10$0.6k0bXmvmXZ8jJ8QqNbMe2P8pRZR8wL7fv/6.v8P1HCY8kYGyJGK
-- password123 -> $2a$10$WmR0nHVW3yyPwM8bNkLHAOy6YEeVH1rYyPjvPZEUNb0mIVJqWq0ni

INSERT INTO users (username, password, full_name, email, role, enabled)
VALUES
    ('admin', '$2a$10$0.6k0bXmvmXZ8jJ8QqNbMe2P8pRZR8wL7fv/6.v8P1HCY8kYGyJGK', 'System Admin', 'admin@clinic.com', 'ADMIN', TRUE),
    ('drsmith', '$2a$10$WmR0nHVW3yyPwM8bNkLHAOy6YEeVH1rYyPjvPZEUNb0mIVJqWq0ni', 'Dr. John Smith', 'drsmith@clinic.com', 'DOCTOR', TRUE),
    ('patient01', '$2a$10$WmR0nHVW3yyPwM8bNkLHAOy6YEeVH1rYyPjvPZEUNb0mIVJqWq0ni', 'Alice Nguyen', 'alice@example.com', 'PATIENT', TRUE);

-- Verify users were inserted
SELECT id, username, enabled, role FROM clinic_db.users;

-- Optional: Map users to roles
-- INSERT INTO user_roles (user_id, role_id) VALUES (1, 1); -- admin -> ROLE_ADMIN
-- INSERT INTO user_roles (user_id, role_id) VALUES (2, 2); -- drsmith -> ROLE_DOCTOR
-- INSERT INTO user_roles (user_id, role_id) VALUES (3, 3); -- patient01 -> ROLE_PATIENT

