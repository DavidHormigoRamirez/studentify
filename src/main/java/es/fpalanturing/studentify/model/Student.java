package es.fpalanturing.studentify.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Entity representing a Student in the system.
 * Mapped to the student table in the database.
 */
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;
    
    @Column(nullable = false, unique = true, length = 255)
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;

    /**
     * Constructor with parameters.
     * @param name the student's name
     * @param email the student's email address
     */
    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Default constructor required by JPA.
     */
    protected Student() {
    }

    /**
     * Gets the student's unique identifier.
     * @return the student's ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the student's unique identifier.
     * @param id the student's ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the student's name.
     * @return the student's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the student's name.
     * @param name the student's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the student's email address.
     * @return the student's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the student's email address.
     * @param email the student's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
