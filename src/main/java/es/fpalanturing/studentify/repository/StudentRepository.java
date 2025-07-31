package es.fpalanturing.studentify.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import es.fpalanturing.studentify.model.Student;

/**
 * Repository interface for Student entity data access operations.
 * Extends CrudRepository to provide basic CRUD operations for Student entities.
 */
@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
    
    /**
     * Finds a student by email address.
     * @param email the email to search for
     * @return Optional containing the student if found, empty otherwise
     */
    java.util.Optional<Student> findByEmail(String email);
    
    /**
     * Checks if a student exists with the given email.
     * @param email the email to check
     * @return true if a student with the email exists, false otherwise
     */
    boolean existsByEmail(String email);
}
