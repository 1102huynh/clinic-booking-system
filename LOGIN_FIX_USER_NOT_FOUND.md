# Login Failed - User Not Found - FIX GUIDE

## Problem Summary

Your login failed with error:
```
User not found: admin
```

This means the `admin` user (and other test users) don't exist in your database.

---

## Solution: Insert Test Users into Database

### Step 1: Open MySQL Client

Open your MySQL command line or MySQL Workbench:
```bash
mysql -u root -p
# Enter password: root
```

### Step 2: Run the INSERT Script

Copy and execute this SQL script in your MySQL client:

```sql
USE clinic_db;

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
```

### Step 3: Verify Users Are Inserted

You should see output like:
```
| id | username   | enabled | role    |
|----|-----------|---------|---------|
| 1  | admin     | 1       | ADMIN   |
| 2  | drsmith   | 1       | DOCTOR  |
| 3  | patient01 | 1       | PATIENT |
```

### Step 4: Try Login Again

Now try logging in with:
- **Username:** `admin`
- **Password:** `admin123`

Or:
- **Username:** `drsmith`
- **Password:** `password123`

---

## Alternative: Use the SQL Script File

I've created a file: `INSERT_TEST_USERS.sql` in your project root.

You can run it directly:
```bash
mysql -u root -p < INSERT_TEST_USERS.sql
```

---

## Why This Happened

1. **Database was reset** - Either Hibernate ddl-auto dropped tables or database was recreated
2. **Sample data not inserted** - The CREATE TABLE script doesn't include INSERT statements
3. **Result:** Users table exists but is empty

---

## What's in the New Users

| Username | Password | Role | Status |
|----------|----------|------|--------|
| admin | admin123 | ADMIN | Enabled |
| drsmith | password123 | DOCTOR | Enabled |
| patient01 | password123 | PATIENT | Enabled |

All passwords are **BCrypt encoded** and will match the Spring Security password encoder.

---

## Verification Checklist

After running the SQL script, verify:

✅ Users exist in database:
```sql
SELECT COUNT(*) FROM clinic_db.users;
-- Should return: 3
```

✅ Admin user specifically exists:
```sql
SELECT * FROM clinic_db.users WHERE username = 'admin';
-- Should return one row with enabled = 1
```

✅ Password is BCrypt encoded:
```sql
SELECT username, password FROM clinic_db.users;
-- Passwords should start with: $2a$10$
```

---

## If Login Still Fails

1. **Check the logs again** for the exact error message
2. **Verify MySQL is running**: `mysql -u root -p -e "SELECT 1;"`
3. **Verify database exists**: `SHOW DATABASES;`
4. **Verify users table**: `SELECT * FROM clinic_db.users;`
5. **Verify user is enabled**: The `enabled` column should be `1` (TRUE)

---

## Expected Login Flow After Fix

1. ✅ User enters username: `admin` and password: `admin123`
2. ✅ CustomUserDetailsService loads user from database
3. ✅ BCrypt compares passwords and they match
4. ✅ User is authenticated
5. ✅ Redirected to `/dashboard`
6. ✅ Login successful!

---

**NOW: Run the SQL script above in your MySQL client to insert the test users!**

