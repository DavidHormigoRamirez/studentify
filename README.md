# Studentify - Student Management API

A Spring Boot REST API for managing student records with health monitoring capabilities.

## Features

- **Student Management**: Create and retrieve student records
- **Health Monitoring**: API health check endpoint
- **Data Validation**: Comprehensive input validation with meaningful error messages
- **Database Integration**: JPA/Hibernate with MySQL support (H2 for testing)
- **Comprehensive Testing**: Unit tests, integration tests, and controller tests
- **Security**: Input validation and error handling

## API Endpoints

### Students

#### GET `/api/students`
Retrieves all students from the system.

**Response:**
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com"
  }
]
```

#### POST `/api/students`
Creates a new student.

**Request Body:**
```json
{
  "name": "Jane Smith",
  "email": "jane.smith@example.com"
}
```

**Response:** `201 Created`
```json
{
  "id": 2,
  "name": "Jane Smith",
  "email": "jane.smith@example.com"
}
```

**Validation Rules:**
- `name`: Required, 2-100 characters
- `email`: Required, valid email format, unique, max 255 characters

### Health Check

#### GET `/api/health`
Returns API health status.

**Response:**
```json
{
  "status": "success",
  "message": "API is healthy",
  "timestamp": "2025-01-31T10:30:45"
}
```

## Technology Stack

- **Java 21** - Programming language
- **Spring Boot 3.4.1** - Application framework
- **Spring Data JPA** - Data persistence
- **MySQL** - Production database
- **H2** - In-memory database for testing
- **JUnit 5** - Testing framework
- **Mockito** - Mocking framework
- **Gradle** - Build tool

## Getting Started

### Prerequisites

- Java 21 or higher
- MySQL database (for production)

### Building the Project

The project uses Gradle with wrapper scripts, so you don't need to install Gradle separately.

**Linux/macOS:**
```bash
./gradlew build
```

**Windows:**
```cmd
gradlew.bat build
```

### Running the Application

**Development (with H2 in-memory database):**
```bash
./gradlew bootRun
```

**Production (configure MySQL first):**
1. Update `application.properties` with your MySQL connection details
2. Run: `./gradlew bootRun --args='--spring.profiles.active=production'`

### Running Tests

**All tests:**
```bash
./gradlew test
```

**Integration tests only:**
```bash
./gradlew test --tests "*Integration*"
```

## Database Configuration

### Development (H2 - Default)
The application uses H2 in-memory database by default for development and testing.

### Production (MySQL)
Add these properties to `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/studentify
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

## Project Structure

```
src/
├── main/java/es/fpalanturing/studentify/
│   ├── controller/          # REST controllers
│   ├── model/              # Entity and DTO classes
│   ├── repository/         # Data access layer
│   └── service/            # Business logic layer
└── test/java/es/fpalanturing/studentify/
    ├── controller/         # Controller unit tests
    ├── service/           # Service unit tests
    └── integration/       # Integration tests
```

## Error Handling

The API provides meaningful error responses:

- `400 Bad Request` - Invalid input data
- `500 Internal Server Error` - Server errors (duplicate email, database issues)

## Security Considerations

- Input validation on all endpoints
- Email uniqueness constraint
- Proper error handling without exposing sensitive information
- Data sanitization (trimming whitespace)

## Development

### Code Style
- Follow Java naming conventions
- Use Javadoc for public methods and classes
- Maintain comprehensive test coverage
- Handle exceptions appropriately

### Testing Strategy
- **Unit Tests**: Test individual components in isolation
- **Integration Tests**: Test complete request-response flow
- **Controller Tests**: Test REST endpoints with MockMvc

## Contributing

1. Fork the repository
2. Create a feature branch
3. Add tests for new functionality
4. Ensure all tests pass
5. Submit a pull request

## License

This project is developed for educational purposes.