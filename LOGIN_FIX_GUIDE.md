# Login Fix Guide - Clinic Booking System

## Problem Summary
Your login was failing because of **TWO critical issues**:

### Issue 1: Plain Text Passwords
The sample data in your database had **plain text passwords** (like "admin123"), but Spring Security uses **BCrypt encoding** to hash passwords. When you tried to login, the system compared the plain text input with a plain text hash - they don't match!

### Issue 2: Schema Mismatch
Your original database schema used a `status` column (ACTIVE/INACTIVE), but your Java code expected an `enabled` boolean column. This caused users to appear as disabled.

---

## Solution Steps

### Step 1: Update Your Database with BCrypt Passwords

**Run this SQL script in your MySQL client:**

```sql
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
SELECT id, username, enabled FROM clinic_db.users;
```

Or use the migration script provided:
```sql
source /path/to/fix_password_encoding.sql;
```

### Step 2: Ensure the `enabled` Column Exists

If your users table still has the old `status` column instead of `enabled`, run this migration:

```sql
-- Add enabled column if it doesn't exist
ALTER TABLE clinic_db.users ADD COLUMN enabled BOOLEAN DEFAULT TRUE;

-- Map old status values to enabled
UPDATE clinic_db.users SET enabled = TRUE WHERE status = 'ACTIVE';
UPDATE clinic_db.users SET enabled = FALSE WHERE status = 'INACTIVE';

-- Make sure all are enabled
UPDATE clinic_db.users SET enabled = TRUE;
```

### Step 3: Rebuild Your Application

After updating the database:

```bash
# Clean and rebuild
mvn clean install

# Or just restart the Spring Boot application
```

### Step 4: Test Login

Try logging in with these credentials:

| Username | Password | Role |
|----------|----------|------|
| admin | admin123 | ADMIN |
| drsmith | password123 | DOCTOR |
| patient01 | password123 | PATIENT |

---

## Debugging

If login still fails, check the logs:

1. **Look at the logs file:**
   ```
   logs/clinic-app.log
   ```

2. **Check for these log messages:**
   - `"Attempting to load user: <username>"` - User lookup started
   - `"User found: <username> | Enabled: true | Role: ADMIN"` - User found and enabled
   - `"User loaded successfully"` - Authentication should work if this appears

3. **Database verification:**
   ```sql
   SELECT id, username, password, enabled FROM clinic_db.users;
   ```
   - Verify `enabled = 1` (TRUE)
   - Verify password starts with `$2a$10$` (BCrypt format)

---

## For New Users (Registration)

When users register through your `/auth/register` page:
- The `RegistrationService` automatically BCrypt-encodes passwords using `PasswordEncoder`
- The `enabled` field is automatically set to TRUE
- This is the correct way to handle passwords

**Never insert plain text passwords directly into the database!**

---

## Key Files Updated

1. **clinic_db.sql** - Updated schema with `enabled` column and BCrypt passwords
2. **fix_password_encoding.sql** - Migration script for existing databases
3. **CustomUserDetailsService.java** - Added detailed logging for debugging
4. **CustomAuthenticationFailureHandler.java** - Enhanced error messages
5. **login.html** - Shows detailed error messages from the server
6. **application.properties** - Enabled DEBUG logging for authentication

---

## Common Issues

### "User not found or account is disabled"
- Check if user exists in database: `SELECT * FROM clinic_db.users WHERE username = 'admin';`
- Check if `enabled = 1` (TRUE)

### "Invalid username or password"
- Password is not BCrypt encoded
- Plain text password stored in database
- Solution: Run the fix_password_encoding.sql script

### "Connection refused / Cannot connect to database"
- Check MySQL is running
- Verify database credentials in `application.properties`
- Check if clinic_db database exists

---

## Need to Create New Test Users?

Use this registration flow OR insert directly with BCrypt:

```sql
-- Generate BCrypt hash (or use an online tool like bcryptkeygenerator.com)
-- Replace with actual BCrypt hash
INSERT INTO clinic_db.users (username, password, full_name, email, role, enabled)
VALUES ('testuser', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Test User', 'test@example.com', 'PATIENT', TRUE);
```

**Important:** Always use BCrypt encoding for passwords stored in the database!

