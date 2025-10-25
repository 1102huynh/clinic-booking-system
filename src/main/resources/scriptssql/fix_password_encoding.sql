-- ===========================================================
-- Fix existing users with BCrypt encoded passwords
-- This script updates the existing users table
-- ===========================================================

-- Update existing users with BCrypt encoded passwords
-- admin123 -> $2a$10$slYQmyNdGzin7olVN3p5aOWDff8rIK4nVu.OtQQNYTpd0/MV2yJwm
-- password123 -> $2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2

UPDATE clinic_db.users
SET password = '$2a$10$slYQmyNdGzin7olVN3p5aOWDff8rIK4nVu.OtQQNYTpd0/MV2yJwm'
WHERE username = 'admin';

UPDATE clinic_db.users
SET password = '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2'
WHERE username = 'drsmith';

UPDATE clinic_db.users
SET password = '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2'
WHERE username = 'patient01';

-- Ensure all users are enabled
UPDATE clinic_db.users SET enabled = TRUE;

-- Verify the updates
SELECT id, username, password, enabled FROM clinic_db.users;

