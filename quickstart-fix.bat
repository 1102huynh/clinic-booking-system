@echo off
REM Quick Start Script for Clinic Booking System Login Fix

echo.
echo ========================================
echo Clinic Booking System - Login Fix
echo ========================================
echo.

echo Step 1: Building the application...
cd D:\learn\clinic-booking-system
call mvn clean install -q
if %errorlevel% neq 0 (
    echo ERROR: Build failed!
    pause
    exit /b 1
)

echo Step 2: Starting the application...
echo Application starting... Please wait for "Started" message
call mvn spring-boot:run -q

echo.
echo ========================================
echo Application is running!
echo ========================================
echo.
echo Next steps:
echo 1. Open your browser
echo 2. Go to: http://localhost:8080/password-generator/generate
echo 3. Click "Fix Admin Passwords Now"
echo 4. Test login at: http://localhost:8080/auth/login
echo.
echo Credentials:
echo   Username: admin
echo   Password: admin123
echo.
pause

