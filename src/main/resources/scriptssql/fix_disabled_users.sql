-- Fix disabled user issue
-- Enable all users in the database
UPDATE clinic_db.users SET enabled = true WHERE enabled = false;

-- Alternatively, if you want to enable a specific user:
-- UPDATE clinic_db.users SET enabled = true WHERE username = 'patient01';

-- Verify the users are enabled:
SELECT id, username, enabled FROM clinic_db.users;

