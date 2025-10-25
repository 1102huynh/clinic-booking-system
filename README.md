# Clinic Booking System

A Spring Boot application for clinic appointment booking with user authentication and role-based access control.

## Features

- User authentication (login/registration)
- Role-based access control (Admin, Doctor, Patient)
- Appointment management
- Doctor and patient management
- MySQL database integration
- Spring Security with BCrypt password encoding

## Technology Stack

- **Backend**: Spring Boot 3.x
- **Database**: MySQL 8.0+
- **Security**: Spring Security 6.x with BCrypt
- **ORM**: Spring Data JPA / Hibernate
- **Frontend**: Thymeleaf, HTML5, CSS3
- **Build Tool**: Maven

## Prerequisites

- Java 17+
- Maven 3.8+
- MySQL 8.0+
- Git

## Setup Instructions

### 1. Clone the Repository
```bash
git clone <repository-url>
cd clinic-booking-system
```

### 2. Database Setup

Run the complete database initialization script:
```sql
mysql -u root -p < src/main/resources/scriptssql/complete_setup.sql
```

Or run the migration script if you have an existing database:
```sql
mysql -u root -p < src/main/resources/scriptssql/migrate_to_enabled_column.sql
```

### 3. Update Application Configuration

Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/clinic_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
```

### 4. Build and Run

```bash
mvn clean install
mvn spring-boot:run
```

The application will start at `http://localhost:8080`

## Default Credentials

| Username | Password | Role |
|----------|----------|------|
| admin | admin123 | ADMIN |
| drsmith | password123 | DOCTOR |
| patient01 | password123 | PATIENT |

## Project Structure

```
src/
├── main/
│   ├── java/huynh/tdt/clinicbookingsystem/
│   │   ├── controller/          # REST/Web controllers
│   │   ├── service/             # Business logic
│   │   ├── entity/              # JPA entities
│   │   ├── repository/          # Data access layer
│   │   ├── security/            # Security handlers
│   │   ├── config/              # Spring configuration
│   │   ├── dto/                 # Data transfer objects
│   │   ├── exception/           # Custom exceptions
│   │   ├── mapper/              # Object mappers
│   │   └── util/                # Utility classes
│   └── resources/
│       ├── application.properties
│       ├── scriptssql/          # Database scripts
│       └── templates/           # Thymeleaf templates
└── test/                        # Unit tests
```

## API Endpoints

### Authentication
- `POST /auth/login` - User login
- `POST /auth/register` - User registration
- `GET /auth/login` - Login page
- `GET /auth/register` - Registration page

### Dashboard
- `GET /dashboard` - Dashboard (authenticated users only)

### Utilities (Development Only)
- `GET /debug/users` - Debug user information
- `GET /password-generator/generate` - Generate BCrypt password hashes

## Security

- Passwords are BCrypt-encoded (never stored as plain text)
- Spring Security protects all endpoints
- CSRF protection disabled for API calls (can be enabled if needed)
- Session-based authentication with HttpSession

## Login Troubleshooting

If you encounter login issues:

1. **Check logs**: `logs/clinic-app.log`
2. **Verify users exist**: 
   ```sql
   SELECT id, username, enabled FROM clinic_db.users;
   ```
3. **Generate new password hash**:
   - Visit `http://localhost:8080/password-generator/generate`
   - Copy the SQL update statements
   - Run them in your database

See `LOGIN_FIX_COMPLETE.md` for detailed troubleshooting steps.

## Important Notes

### Adding New Users

**Option 1: Registration (Recommended)**
- Users can self-register via `/auth/register`
- Passwords are automatically BCrypt-encoded

**Option 2: Database Insert (Admin Only)**
```sql
-- Generate hash using /password-generator/generate endpoint
-- Then insert:
UPDATE clinic_db.users SET password = '$2a$10$...' WHERE username = 'username';
```

## Development

### Build
```bash
mvn clean install
```

### Run Tests
```bash
mvn test
```

### Create Executable JAR
```bash
mvn clean package
```

The JAR will be located at: `target/clinic-booking-system-0.0.1-SNAPSHOT.jar`

### Run JAR
```bash
java -jar target/clinic-booking-system-0.0.1-SNAPSHOT.jar
```

## Future Enhancements

- [ ] Email notifications
- [ ] Appointment rescheduling
- [ ] Doctor availability management
- [ ] Patient medical records
- [ ] Payment integration
- [ ] Admin analytics dashboard
- [ ] API documentation (Swagger/OpenAPI)
- [ ] Mobile application
- [ ] Two-factor authentication

## Troubleshooting

### Database Connection Failed
- Check MySQL is running
- Verify credentials in `application.properties`
- Check clinic_db exists: `SHOW DATABASES;`

### Login Fails with "Invalid username or password"
- Verify users in database with correct BCrypt hashes
- Check user `enabled` field is TRUE (1)
- See LOGIN_FIX_COMPLETE.md for detailed steps

### Port 8080 Already in Use
```bash
# Use a different port
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For issues or questions, please refer to:
- `LOGIN_FIX_GUIDE.md` - Login issue resolution
- `TROUBLESHOOTING_GUIDE.md` - General troubleshooting
- `LOGIN_FIX_COMPLETE.md` - Complete fix summary

## Authors

- Huynh Tran Duc Thinh - Development

## Changelog

### Version 0.0.1 (October 25, 2025)
- Initial project setup
- User authentication system
- Role-based access control
- Database schema with users, roles, doctors, patients, appointments
- Login page with error handling
- Registration page
- Dashboard
- Security configuration with BCrypt password encoding
- Debug endpoints for troubleshooting
- Password generator utility

