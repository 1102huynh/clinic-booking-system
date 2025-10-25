#!/bin/bash
# Test Runner Script for Clinic Booking System
# This script runs the Maven tests and captures detailed output

echo "============================================"
echo "Clinic Booking System - Test Execution Log"
echo "============================================"
echo "Date: $(date)"
echo "Working Directory: $(pwd)"
echo ""

# Step 1: Clean
echo "[STEP 1] Running Maven Clean..."
mvn clean

echo ""
echo "============================================"
echo "[STEP 2] Compiling Tests..."
echo "============================================"
mvn test-compile

echo ""
echo "============================================"
echo "[STEP 3] Running Unit Tests..."
echo "============================================"
mvn test -Dorg.slf4j.simpleLogger.defaultLogLevel=debug

echo ""
echo "============================================"
echo "[STEP 4] Test Results Summary"
echo "============================================"
# Check if tests passed
if [ $? -eq 0 ]; then
    echo "✓ ALL TESTS PASSED!"
else
    echo "✗ SOME TESTS FAILED - See output above"
fi

echo ""
echo "============================================"
echo "[STEP 5] Generating Coverage Report"
echo "============================================"
mvn jacoco:report

echo ""
echo "Test execution completed!"
echo "Coverage report: target/site/jacoco/index.html"

