@echo off
REM Test Runner Script for Clinic Booking System (Windows)
REM This script runs the Maven tests and captures detailed output

echo.
echo ============================================
echo Clinic Booking System - Test Execution Log
echo ============================================
echo Date: %date% %time%
echo Working Directory: %cd%
echo.

REM Step 1: Clean
echo [STEP 1] Running Maven Clean...
call mvn clean
if errorlevel 1 (
    echo ERROR: Maven clean failed
    exit /b 1
)

echo.
echo ============================================
echo [STEP 2] Compiling Tests...
echo ============================================
call mvn test-compile
if errorlevel 1 (
    echo ERROR: Test compilation failed
    exit /b 1
)

echo.
echo ============================================
echo [STEP 3] Running Unit Tests...
echo ============================================
call mvn test
if errorlevel 1 (
    echo SOME TESTS FAILED - See output above
) else (
    echo.
    echo ✓ ALL TESTS PASSED!
)

echo.
echo ============================================
echo [STEP 4] Test Summary
echo ============================================
echo Check the output above for test results

echo.
echo Test execution completed!
pause

