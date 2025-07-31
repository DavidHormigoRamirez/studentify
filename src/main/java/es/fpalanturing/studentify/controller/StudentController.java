package es.fpalanturing.studentify.controller;

import es.fpalanturing.studentify.model.Student;
import es.fpalanturing.studentify.model.StudentDao;
import es.fpalanturing.studentify.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * REST controller for managing student operations.
 * Provides endpoints for creating and retrieving students.
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;

    /**
     * Constructor for StudentController.
     * @param service the student service to handle business logic
     */
    public StudentController(StudentService service) {
        this.service = service;
    }

    /**
     * Retrieves all students from the system.
     * @return ResponseEntity containing list of all students
     */
    @GetMapping
    public ResponseEntity<Iterable<Student>> getAllStudents() {
        try {
            Iterable<Student> students = service.readAllStudents();
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Creates a new student in the system.
     * @param studentDao the student data transfer object containing student information
     * @return ResponseEntity containing the created student or error status
     */
    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody StudentDao studentDao) {
        try {
            if (studentDao == null || studentDao.getName() == null || studentDao.getEmail() == null) {
                return ResponseEntity.badRequest().build();
            }
            
            if (studentDao.getName().trim().isEmpty() || studentDao.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            Student newStudent = new Student(studentDao.getName().trim(), studentDao.getEmail().trim());
            Student createdStudent = service.createStudent(newStudent);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
