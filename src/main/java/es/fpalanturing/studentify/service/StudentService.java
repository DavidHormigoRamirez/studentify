package es.fpalanturing.studentify.service;

import es.fpalanturing.studentify.model.Student;

/**
 * Service interface for managing student operations.
 * Defines the contract for student business logic operations.
 */
public interface StudentService {

    /**
     * Retrieves all students from the system.
     * @return Iterable containing all students in the system
     */
    Iterable<Student> readAllStudents();

    /**
     * Creates a new student in the system.
     * @param student the student to create
     * @return the created student with assigned ID
     * @throws IllegalArgumentException if student data is invalid
     * @throws RuntimeException if operation fails
     */
    Student createStudent(Student student);
}
