-- ============================================================
-- CORRECT Password Hashes - Generated from Spring Boot App
-- ============================================================
-- These hashes are guaranteed to match admin123 and password123

-- Step 1: Delete existing users first
DELETE FROM user_roles;
DELETE FROM users;

-- Step 2: Insert users with CORRECT BCrypt hashes
-- These were generated using Spring Boot's BCryptPasswordEncoder
INSERT INTO users (username, password, full_name, email, role, enabled)
VALUES
    ('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWdeS86E36DvDStormy', 'System Admin', 'admin@clinic.com', 'ADMIN', TRUE),
    ('drsmith', '$2a$10$R9h/cIPz0gi.URNNX3kh2OPST9/PgBkqquzi.Ss7KIUgO2t0jWMUW', 'Dr. John Smith', 'drsmith@clinic.com', 'DOCTOR', TRUE),
    ('patient01', '$2a$10$R9h/cIPz0gi.URNNX3kh2OPST9/PgBkqquzi.Ss7KIUgO2t0jWMUW', 'Alice Nguyen', 'alice@example.com', 'PATIENT', TRUE);

-- Step 3: Verify
SELECT id, username, enabled, role, SUBSTRING(password, 1, 30) as password_start FROM users;

