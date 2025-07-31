package es.fpalanturing.studentify.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.fpalanturing.studentify.model.Student;
import es.fpalanturing.studentify.repository.StudentRepository;

/**
 * Database implementation of the StudentService interface.
 * Handles all student-related business logic and database operations.
 */
@Service
@Transactional
public class StudentServiceDatabase implements StudentService {

    private final StudentRepository repository;

    /**
     * Constructor for dependency injection.
     * @param repository the student repository for database operations
     */
    public StudentServiceDatabase(StudentRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves all students from the database.
     * @return Iterable containing all students
     * @throws RuntimeException if database operation fails
     */
    @Override
    @Transactional(readOnly = true)
    public Iterable<Student> readAllStudents() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve students from database", e);
        }
    }

    /**
     * Creates a new student in the database.
     * @param student the student entity to create
     * @return the created student with generated ID
     * @throws IllegalArgumentException if student data is invalid
     * @throws RuntimeException if student with email already exists or database operation fails
     */
    @Override
    public Student createStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be null or empty");
        }
        
        if (student.getEmail() == null || student.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Student email cannot be null or empty");
        }
        
        try {
            return repository.save(student);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Student with email " + student.getEmail() + " already exists", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create student", e);
        }
    }
}
