-- ===========================================================
-- Update Users with CORRECT BCrypt Passwords
-- ===========================================================
-- These are the ACTUAL BCrypt hashes that will match:
-- admin123 -> $2a$10$0.6k0bXmvmXZ8jJ8QqNbMe2P8pRZR8wL7fv/6.v8P1HCY8kYGyJGK
-- password123 -> $2a$10$WmR0nHVW3yyPwM8bNkLHAOy6YEeVH1rYyPjvPZEUNb0mIVJqWq0ni

-- BACKUP YOUR CURRENT USERS FIRST (just in case)
-- SELECT * FROM clinic_db.users;

-- Update admin user
UPDATE clinic_db.users
SET password = '$2a$10$0.6k0bXmvmXZ8jJ8QqNbMe2P8pRZR8wL7fv/6.v8P1HCY8kYGyJGK'
WHERE username = 'admin';

-- Update drsmith user
UPDATE clinic_db.users
SET password = '$2a$10$WmR0nHVW3yyPwM8bNkLHAOy6YEeVH1rYyPjvPZEUNb0mIVJqWq0ni'
WHERE username = 'drsmith';

-- Update patient01 user
UPDATE clinic_db.users
SET password = '$2a$10$WmR0nHVW3yyPwM8bNkLHAOy6YEeVH1rYyPjvPZEUNb0mIVJqWq0ni'
WHERE username = 'patient01';

-- Verify the updates
SELECT id, username, password, enabled FROM clinic_db.users;

