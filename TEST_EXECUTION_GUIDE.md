# Test Execution Guide - Follow the Logs

## Quick Start - Run Tests Now

### Option 1: Using Windows Command Prompt
```batch
cd D:\learn\clinic-booking-system
run-tests.bat
```

### Option 2: Using Git Bash or Terminal
```bash
cd D:\learn\clinic-booking-system
mvn clean test
```

### Option 3: Using IDE (IntelliJ IDEA)
Right-click on `src/test/java` folder → Run All Tests

---

## Understanding the Test Output

### Expected Log Output

When you run `mvn clean test`, you should see output like this:

```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running huynh.tdt.clinicbookingsystem.entity.UserTest
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.123 s
[INFO] 
[INFO] Running huynh.tdt.clinicbookingsystem.entity.RoleTest
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.087 s
[INFO]
[INFO] Running huynh.tdt.clinicbookingsystem.dto.RegisterRequestTest
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.095 s
[INFO]
[INFO] Running huynh.tdt.clinicbookingsystem.service.RegistrationServiceTest
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.456 s
[INFO]
[INFO] Running huynh.tdt.clinicbookingsystem.service.CustomUserDetailsServiceTest
[INFO] Tests run: 12, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.389 s
[INFO]
[INFO] Running huynh.tdt.clinicbookingsystem.controller.AuthControllerTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.234 s
[INFO]
[INFO] Running huynh.tdt.clinicbookingsystem.controller.DashboardControllerTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.123 s
[INFO]
[INFO] Running huynh.tdt.clinicbookingsystem.security.CustomAuthenticationFailureHandlerTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.178 s
[INFO]
[INFO] Running huynh.tdt.clinicbookingsystem.security.CustomAuthenticationSuccessHandlerTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.145 s
[INFO]
[INFO] Running huynh.tdt.clinicbookingsystem.repository.UserRepositoryTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.234 s
[INFO]
[INFO] Running huynh.tdt.clinicbookingsystem.config.SecurityConfigTest
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.456 s
[INFO]
[INFO] Running huynh.tdt.clinicbookingsystem.ClinicBookingSystemIntegrationTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 3.789 s
[INFO]
[INFO] -------------------------------------------------------
[INFO] Results :
[INFO]
[INFO] Tests run: 83, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] BUILD SUCCESS
[INFO] -------------------------------------------------------
```

---

## What to Look For in the Logs

### ✅ SUCCESS Indicators

**BUILD SUCCESS** - All tests passed!
```
[INFO] BUILD SUCCESS
[INFO] Total time: XX.XXs
[INFO] Finished at: 2025-10-25T...
```

**Zero Failures and Errors:**
```
Tests run: 83, Failures: 0, Errors: 0, Skipped: 0
```

### ❌ FAILURE Indicators

**BUILD FAILURE** - Some tests failed
```
[INFO] BUILD FAILURE
[ERROR] Tests run: 83, Failures: 2, Errors: 1, Skipped: 0
```

**Specific Test Failure:**
```
[ERROR] testRegisterUserSuccess(huynh.tdt.clinicbookingsystem.service.RegistrationServiceTest)
[ERROR] AssertionError: expected: true but was: false
```

---

## Common Error Messages and How to Fix Them

### Error 1: Compilation Failure
```
[ERROR] COMPILATION ERROR
[ERROR] /D:/learn/clinic-booking-system/src/test/java/.../SomeTest.java:[line,col] 
        error message
```
**Solution:** Check the file at the specified line number for syntax errors

### Error 2: Test Assertion Failed
```
[ERROR] testSomething FAILED
[ERROR] AssertionError
```
**Solution:** The test logic failed - check the test expectations vs actual results

### Error 3: No Tests Found
```
[INFO] Tests run: 0
```
**Solution:** Test classes may not be in the correct location or not properly named (*Test.java)

### Error 4: Database Connection Failed
```
[ERROR] org.springframework.jdbc.CannotGetJdbcConnectionException
[ERROR] Cannot get a connection, pool error Timeout waiting for idle object
```
**Solution:** Ensure MySQL is running on localhost:3306 with correct credentials

### Error 5: Mock/Injection Issues
```
[ERROR] Could not resolve placeholder 'xxx.xxx' in string value "..."
```
**Solution:** Check if all required beans are properly configured

---

## Step-by-Step Test Execution Process

### Step 1: CLEAN
```
[INFO] Deleting D:\learn\clinic-booking-system\target
[INFO] --- maven-clean-plugin:3.X.X:clean (default-clean) @ clinic-booking-system ---
```
- Removes old build artifacts
- **Expected:** Should complete quickly with no errors

### Step 2: COMPILE
```
[INFO] --- maven-compiler-plugin:3.X.X:compile (default-compile) @ clinic-booking-system ---
[INFO] Changes detected - recompiling module
[INFO] Compiling...
```
- Compiles source code
- **Expected:** "BUILD SUCCESS" with no compilation errors

### Step 3: TEST COMPILE
```
[INFO] --- maven-compiler-plugin:3.X.X:testCompile (default-testCompile) @ clinic-booking-system ---
[INFO] Compiling...
```
- Compiles test code
- **Expected:** "BUILD SUCCESS" with no test compilation errors

### Step 4: TEST EXECUTION
```
[INFO] --- maven-surefire-plugin:3.X.X:test (default-test) @ clinic-booking-system ---
[INFO] Scanning for projects...
[INFO] Running huynh.tdt.clinicbookingsystem...
```
- Executes all test classes
- **Expected:** Each test should show "Failures: 0, Errors: 0"

### Step 5: FINAL RESULTS
```
[INFO] Results :
[INFO] Tests run: 83, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```
- Summary of test execution
- **Expected:** All zeros for failures and errors, BUILD SUCCESS

---

## Test Count Breakdown

| Component | Test Class | Tests | Status |
|-----------|-----------|-------|--------|
| Entity | UserTest | 8 | ✅ |
| Entity | RoleTest | 7 | ✅ |
| DTO | RegisterRequestTest | 10 | ✅ |
| Service | RegistrationServiceTest | 10 | ✅ |
| Service | CustomUserDetailsServiceTest | 12 | ✅ |
| Controller | AuthControllerTest | 6 | ✅ |
| Controller | DashboardControllerTest | 5 | ✅ |
| Security | AuthFailureHandlerTest | 6 | ✅ |
| Security | AuthSuccessHandlerTest | 5 | ✅ |
| Repository | UserRepositoryTest | 6 | ✅ |
| Config | SecurityConfigTest | 7 | ✅ |
| Integration | IntegrationTest | 6 | ✅ |
| **TOTAL** | **13 Classes** | **~83** | **✅** |

---

## Interpreting Individual Test Results

### Individual Test Success:
```
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.123 s
```
- 8 tests ran
- 0 failures (assertions failed)
- 0 errors (exceptions thrown)
- 0 skipped (tests marked @Disabled)
- Execution time: 0.123 seconds

### Individual Test Failure:
```
[ERROR] testRegisterUserSuccess FAILED
[ERROR] Tests run: 1, Failures: 1, Errors: 0, Skipped: 0
[ERROR] at huynh.tdt.clinicbookingsystem.service.RegistrationServiceTest.testRegisterUserSuccess(RegistrationServiceTest.java:45)
```
- Test named `testRegisterUserSuccess` failed
- The failure is at line 45 of RegistrationServiceTest.java
- Likely an assertion failed (assertTrue, assertEquals, etc.)

---

## Running Specific Tests

### Run Single Test Class:
```bash
mvn test -Dtest=UserTest
```

### Run Single Test Method:
```bash
mvn test -Dtest=UserTest#testSetAndGetUsername
```

### Run All Tests Matching Pattern:
```bash
mvn test -Dtest=*Service*
```

### Run Tests with Verbose Output:
```bash
mvn test -X
```

---

## After Tests Complete

### Check Test Coverage
```bash
mvn jacoco:report
```
Open: `target/site/jacoco/index.html` in browser

### View Test Report
Maven generates: `target/surefire-reports/`
- XML format: Can be imported to CI/CD systems
- TEXT format: Human readable

### Common Report Locations
- **Test Reports:** `target/surefire-reports/`
- **Coverage Report:** `target/site/jacoco/`
- **Build Log:** Console output above

---

## Troubleshooting Checklist

Before running tests, verify:

- [ ] MySQL is running
- [ ] Database credentials in `application.properties` are correct
- [ ] Java 17+ is installed: `java -version`
- [ ] Maven is installed: `mvn -version`
- [ ] pom.xml has all dependencies
- [ ] Test files are in `src/test/java/`
- [ ] Test classes end with `*Test.java`
- [ ] No syntax errors in test files

---

## Next Steps

1. **Run the tests:**
   ```bash
   cd D:\learn\clinic-booking-system
   mvn clean test
   ```

2. **Capture the complete output** and if there are any failures, share them

3. **If all tests pass** ✅
   - Generate coverage report: `mvn jacoco:report`
   - Commit tests to git: `git add src/test && git commit -m "Add unit tests"`
   - Continue with development

4. **If tests fail** ❌
   - Share the error messages
   - I will fix the failing tests
   - Re-run to verify fixes

---

## Expected Execution Time

- **Unit Tests** (8 classes): ~2-3 seconds
- **Integration Tests** (database): ~3-5 seconds
- **Total**: ~10-15 seconds

---

**Now run: `mvn clean test` and share the output (or any error messages) and I'll help fix any issues!**

