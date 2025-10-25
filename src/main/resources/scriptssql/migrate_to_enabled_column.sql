-- ===========================================================
-- Migration Script: Update users table schema
-- This script migrates from 'status' column to 'enabled' column
-- ===========================================================

-- Step 1: Add the 'enabled' column if it doesn't exist
ALTER TABLE clinic_db.users ADD COLUMN enabled BOOLEAN DEFAULT TRUE;

-- Step 2: Update enabled based on existing status (if status column exists)
UPDATE clinic_db.users SET enabled = TRUE WHERE status = 'ACTIVE';
UPDATE clinic_db.users SET enabled = FALSE WHERE status = 'INACTIVE';

-- Step 3: Drop the old 'status' column (optional - comment out if you want to keep it)
-- ALTER TABLE clinic_db.users DROP COLUMN status;

-- Step 4: Create roles table if it doesn't exist
CREATE TABLE IF NOT EXISTS clinic_db.roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Step 5: Create user_roles table if it doesn't exist
CREATE TABLE IF NOT EXISTS clinic_db.user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES clinic_db.users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES clinic_db.roles(id) ON DELETE CASCADE
);

-- Step 6: Insert default roles
INSERT IGNORE INTO clinic_db.roles (name) VALUES ('ROLE_ADMIN');
INSERT IGNORE INTO clinic_db.roles (name) VALUES ('ROLE_DOCTOR');
INSERT IGNORE INTO clinic_db.roles (name) VALUES ('ROLE_PATIENT');

-- Step 7: Verify the changes
SELECT id, username, enabled FROM clinic_db.users;

-- Step 8: Make sure all existing users are enabled
UPDATE clinic_db.users SET enabled = TRUE WHERE enabled IS NULL;

