package es.fpalanturing.studentify.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for Student information.
 * Used for API request/response handling.
 */
public class StudentDao {
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;

    /**
     * Default constructor required for JSON deserialization.
     */
    public StudentDao() {
    }

    /**
     * Constructor with parameters.
     * @param name the student's name
     * @param email the student's email address
     */
    public StudentDao(String name, String email) {
        this.name = name;
        this.email = email;
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
     * Gets the student's email.
     * @return the student's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the student's email.
     * @param email the student's email address
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
