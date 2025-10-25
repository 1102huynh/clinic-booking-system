# Complete Login Troubleshooting Guide

## Quick Fix (DO THIS FIRST)

Run this complete database setup script in your MySQL client to reset everything correctly:

```bash
# Navigate to your project root
cd D:\learn\clinic-booking-system

# Run the complete setup script
mysql -u root -p < src/main/resources/scriptssql/complete_setup.sql
```

When prompted, enter your MySQL password: `root`

---

## What This Script Does

1. **Drops and recreates** the entire database from scratch
2. **Creates all tables** with correct schema (including the `enabled` column)
3. **Inserts sample users** with BCrypt-encoded passwords:
   - Username: `admin` | Password: `admin123` | Role: ADMIN
   - Username: `drsmith` | Password: `password123` | Role: DOCTOR
   - Username: `patient01` | Password: `password123` | Role: PATIENT
4. **Sets all users to enabled = TRUE**

---

## After Running the Script

### Step 1: Verify Users in Database

```sql
-- Run this query to verify users were created correctly
SELECT id, username, enabled, role FROM clinic_db.users;
```

You should see:
```
| id | username   | enabled | role    |
|----|-----------|---------|---------|
| 1  | admin     | 1       | ADMIN   |
| 2  | drsmith   | 1       | DOCTOR  |
| 3  | patient01 | 1       | PATIENT |
```

### Step 2: Rebuild Your Application

Stop your Spring Boot application and rebuild:

```bash
# Clean and rebuild
mvn clean install

# Start the application
mvn spring-boot:run
```

### Step 3: Use the Debug Endpoint

I've created a debug utility at: `http://localhost:8080/debug/users`

This page will show you:
- ✅ All users in the database
- ✅ Whether users are enabled
- ✅ Whether passwords match the test credentials
- ✅ Any issues preventing login

**Visit:** http://localhost:8080/debug/users

Look for a GREEN "true" under the "Matches" column. If you see RED "false", the passwords aren't set up correctly.

### Step 4: Test Login

Go to: http://localhost:8080/auth/login

Try logging in with:
- **Username:** `admin`
- **Password:** `admin123`

---

## If Login STILL Fails

1. **Check the debug page** (`http://localhost:8080/debug/users`)
   - Look for "NO USERS FOUND IN DATABASE" - means users weren't inserted
   - Look for RED "false" under Matches - means password encoding is wrong
   - Look for `enabled = 0` - means user is disabled

2. **Generate a new password** using the debug tool:
   ```
   http://localhost:8080/debug/generate-password?password=testpass123
   ```
   Copy the encoded password and update the user in the database

3. **Check application logs** for detailed error information:
   ```
   tail -f logs/clinic-app.log
   ```

---

## Manual Database Fix (If Script Fails)

If the complete_setup.sql script fails, run these commands one by one:

```sql
-- Connect to MySQL
mysql -u root -p

-- Drop old database
DROP DATABASE IF EXISTS clinic_db;

-- Create new database
CREATE DATABASE clinic_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE clinic_db;

-- Create users table
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

-- Insert test users with BCrypt passwords
INSERT INTO users (username, password, full_name, email, role, enabled)
VALUES 
    ('admin', '$2a$10$slYQmyNdGzin7olVN3p5aOWDff8rIK4nVu.OtQQNYTpd0/MV2yJwm', 'System Admin', 'admin@clinic.com', 'ADMIN', TRUE),
    ('drsmith', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. John Smith', 'drsmith@clinic.com', 'DOCTOR', TRUE),
    ('patient01', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Alice Nguyen', 'alice@example.com', 'PATIENT', TRUE);

-- Verify
SELECT id, username, enabled FROM users;
```

---

## Key Points to Remember

⚠️ **NEVER use plain text passwords in the database!**

✅ **Always use BCrypt encoding** - The application automatically BCrypt-encodes passwords when users register

✅ **The `enabled` column must be `1` (TRUE)** for users to login

✅ **After any database changes, restart the application**

---

## Files I've Added/Modified

1. **`/debug/users`** - New debug endpoint to inspect database (no auth required)
2. **`/debug/generate-password`** - Generate BCrypt hashes for passwords
3. **`complete_setup.sql`** - Complete database initialization script
4. **`SecurityConfig.java`** - Added `/debug/**` to permitAll list
5. **`CustomUserDetailsService.java`** - Added detailed logging
6. **`application.properties`** - Enabled DEBUG logging

---

## Expected Log Output (When Working)

When you login successfully, you should see in `logs/clinic-app.log`:

```
Attempting to load user: admin
User found: admin | Enabled: true | Role: ADMIN
User admin loaded successfully with authorities: [ROLE_ADMIN]
```

If authentication fails, you'll see:

```
Attempting to load user: admin
User found: admin | Enabled: true | Role: ADMIN
(then password mismatch occurs)
```

---

## Need Help?

1. Run `http://localhost:8080/debug/users` and tell me what you see
2. Check the password hashes match the BCrypt format (starts with `$2a$10$`)
3. Verify `enabled = 1` for all users
4. Check application logs for error details

Let me know if this works!

