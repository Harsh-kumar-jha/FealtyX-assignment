# Student Management System

A Spring Boot REST API for managing student records. This system provides CRUD (Create, Read, Update, Delete) operations for student data with proper validation, error handling, and concurrent request handling.

## Features

- Create new student records
- Retrieve student information (single or paginated list of students)
- Update existing student records
- Delete student records (single or all)
- Input validation with comprehensive rules
- Duplicate email prevention
- Comprehensive error handling
- Detailed logging for operations
- Multiple environment support (dev, stage, prod)
- AI-powered student summary generation using Ollama

## Technology Stack

- Java 17
- Spring Boot
- Gradle
- SLF4J (Logging)
- Jakarta Validation
- Lombok
- Ollama AI Integration

## Environment Setup

### 1. Environment Configuration

Create a `.env` file in the project root with the following content:

```properties
# Development Environment
DEV_SERVER_PORT=8080
DEV_OLLAMA_API_URL=http://localhost:11434

# Staging Environment
STAGE_SERVER_PORT=8081
STAGE_OLLAMA_API_URL=http://localhost:11434

# Production Environment
PROD_SERVER_PORT=8082
PROD_OLLAMA_API_URL=http://your-ollama-server:11434
```

## Running the Application

### 1. Development Environment
```bash
# Run with development profile
./gradlew bootRun --args='--spring.profiles.active=dev'

# Application will start on port defined in DEV_SERVER_PORT (default: 8080)
# Access the application at http://localhost:8080
```

### 2. Staging Environment
```bash
# Run with staging profile
./gradlew bootRun --args='--spring.profiles.active=stage'

# Application will start on port defined in STAGE_SERVER_PORT (default: 8081)
# Access the application at http://localhost:8081
```

### 3. Production Environment
```bash
# Run with production profile
./gradlew bootRun --args='--spring.profiles.active=prod'

# Application will start on port defined in PROD_SERVER_PORT (default: 8082)
# Access the application at http://localhost:8082
```

### Verifying Active Profile

1. Check the startup logs - you should see:
   ```
   The following profiles are active: [dev/stage/prod]
   ```

2. Verify the application is running on the correct port:
   - Development: http://localhost:8080
   - Staging: http://localhost:8081
   - Production: http://localhost:8082


## API Documentation

Detailed API documentation is available in [api-docs/studentCRUD.md](api-docs/studentCRUD.md)

### Quick API Reference

- `POST /api/v1/students` - Create a new student
- `GET /api/v1/students/{id}` - Get student by ID
- `GET /api/v1/students` - Get paginated list of students
- `PUT /api/v1/students/{id}` - Update student
- `DELETE /api/v1/students/{id}` - Delete student
- `DELETE /api/v1/students` - Delete all students
- `GET /api/v1/students/{id}/summary` - Get AI-generated student summary

## Validation Rules

- Name: 2-100 characters, required
- Age: Between 16 and 120, required
- Email: Valid email format, unique, max 255 characters, required
- Pagination: Page number ≥ 0, Page size between 1 and 100

## Concurrency Handling

The application implements several mechanisms to handle concurrent requests safely:

1. Optimistic Locking
   - Uses version field to detect concurrent modifications
   - Prevents lost updates in concurrent scenarios

2. Transaction Management
   - Proper isolation levels for data consistency
   - READ_COMMITTED isolation for most operations

3. Thread Safety
   - Thread-safe repository operations
   - Synchronized blocks for critical sections

## Error Handling

The application includes comprehensive error handling for:
- Invalid input data
- Resource not found
- Duplicate email addresses
- Validation errors
- Concurrent modification conflicts
- AI service errors

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details 