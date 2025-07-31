package es.fpalanturing.studentify.service;

import es.fpalanturing.studentify.model.Student;
import es.fpalanturing.studentify.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for StudentServiceDatabase.
 * Tests business logic and database operations for student management.
 */
@ExtendWith(MockitoExtension.class)
class StudentServiceDatabaseTest {

    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentServiceDatabase studentService;

    private Student testStudent;

    @BeforeEach
    void setUp() {
        testStudent = new Student("John Doe", "john.doe@example.com");
        testStudent.setId(1L);
    }

    @Test
    void readAllStudents_ShouldReturnAllStudents() {
        // Arrange
        when(repository.findAll()).thenReturn(Arrays.asList(testStudent));

        // Act
        Iterable<Student> result = studentService.readAllStudents();

        // Assert
        assertNotNull(result);
        assertTrue(result.iterator().hasNext());
        assertEquals(testStudent, result.iterator().next());
        verify(repository).findAll();
    }

    @Test
    void readAllStudents_ShouldReturnEmptyList_WhenNoStudents() {
        // Arrange
        when(repository.findAll()).thenReturn(Collections.emptyList());

        // Act
        Iterable<Student> result = studentService.readAllStudents();

        // Assert
        assertNotNull(result);
        assertFalse(result.iterator().hasNext());
        verify(repository).findAll();
    }

    @Test
    void readAllStudents_ShouldThrowRuntimeException_WhenRepositoryThrowsException() {
        // Arrange
        when(repository.findAll()).thenThrow(new RuntimeException("Database connection failed"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> studentService.readAllStudents());
        
        assertEquals("Failed to retrieve students from database", exception.getMessage());
        verify(repository).findAll();
    }

    @Test
    void createStudent_ShouldReturnCreatedStudent() {
        // Arrange
        Student inputStudent = new Student("Jane Doe", "jane.doe@example.com");
        Student savedStudent = new Student("Jane Doe", "jane.doe@example.com");
        savedStudent.setId(2L);
        
        when(repository.save(inputStudent)).thenReturn(savedStudent);

        // Act
        Student result = studentService.createStudent(inputStudent);

        // Assert
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Jane Doe", result.getName());
        assertEquals("jane.doe@example.com", result.getEmail());
        verify(repository).save(inputStudent);
    }

    @Test
    void createStudent_ShouldThrowIllegalArgumentException_WhenStudentIsNull() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> studentService.createStudent(null));
        
        assertEquals("Student cannot be null", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void createStudent_ShouldThrowIllegalArgumentException_WhenNameIsNull() {
        // Arrange
        Student invalidStudent = new Student(null, "test@example.com");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> studentService.createStudent(invalidStudent));
        
        assertEquals("Student name cannot be null or empty", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void createStudent_ShouldThrowIllegalArgumentException_WhenNameIsEmpty() {
        // Arrange
        Student invalidStudent = new Student("", "test@example.com");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> studentService.createStudent(invalidStudent));
        
        assertEquals("Student name cannot be null or empty", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void createStudent_ShouldThrowIllegalArgumentException_WhenNameIsWhitespace() {
        // Arrange
        Student invalidStudent = new Student("   ", "test@example.com");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> studentService.createStudent(invalidStudent));
        
        assertEquals("Student name cannot be null or empty", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void createStudent_ShouldThrowIllegalArgumentException_WhenEmailIsNull() {
        // Arrange
        Student invalidStudent = new Student("John Doe", null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> studentService.createStudent(invalidStudent));
        
        assertEquals("Student email cannot be null or empty", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void createStudent_ShouldThrowIllegalArgumentException_WhenEmailIsEmpty() {
        // Arrange
        Student invalidStudent = new Student("John Doe", "");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> studentService.createStudent(invalidStudent));
        
        assertEquals("Student email cannot be null or empty", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void createStudent_ShouldThrowIllegalArgumentException_WhenEmailIsWhitespace() {
        // Arrange
        Student invalidStudent = new Student("John Doe", "   ");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> studentService.createStudent(invalidStudent));
        
        assertEquals("Student email cannot be null or empty", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void createStudent_ShouldThrowRuntimeException_WhenEmailAlreadyExists() {
        // Arrange
        Student duplicateStudent = new Student("John Doe", "existing@example.com");
        when(repository.save(duplicateStudent))
                .thenThrow(new DataIntegrityViolationException("Duplicate email"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> studentService.createStudent(duplicateStudent));
        
        assertTrue(exception.getMessage().contains("Student with email existing@example.com already exists"));
        verify(repository).save(duplicateStudent);
    }

    @Test
    void createStudent_ShouldThrowRuntimeException_WhenRepositoryThrowsGeneralException() {
        // Arrange
        Student student = new Student("John Doe", "john@example.com");
        when(repository.save(student))
                .thenThrow(new RuntimeException("Database connection failed"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> studentService.createStudent(student));
        
        assertEquals("Failed to create student", exception.getMessage());
        verify(repository).save(student);
    }
}