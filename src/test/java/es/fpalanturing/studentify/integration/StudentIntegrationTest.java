package es.fpalanturing.studentify.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.fpalanturing.studentify.model.Student;
import es.fpalanturing.studentify.model.StudentDao;
import es.fpalanturing.studentify.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the Student API.
 * Tests the complete flow from controller to database.
 */
@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
class StudentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
    }

    @Test
    void createAndRetrieveStudent_ShouldWorkEndToEnd() throws Exception {
        // Arrange
        StudentDao newStudent = new StudentDao("Alice Johnson", "alice.johnson@example.com");

        // Act: Create student
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStudent)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Alice Johnson"))
                .andExpect(jsonPath("$.email").value("alice.johnson@example.com"))
                .andExpect(jsonPath("$.id").exists());

        // Act: Retrieve all students
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Alice Johnson"))
                .andExpect(jsonPath("$[0].email").value("alice.johnson@example.com"));
    }

    @Test
    void createMultipleStudents_ShouldReturnAllStudents() throws Exception {
        // Arrange
        StudentDao student1 = new StudentDao("John Doe", "john.doe@example.com");
        StudentDao student2 = new StudentDao("Jane Smith", "jane.smith@example.com");

        // Act: Create first student
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student1)))
                .andExpect(status().isCreated());

        // Act: Create second student
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student2)))
                .andExpect(status().isCreated());

        // Act: Retrieve all students
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void createStudentWithDuplicateEmail_ShouldReturnError() throws Exception {
        // Arrange
        StudentDao student1 = new StudentDao("John Doe", "duplicate@example.com");
        StudentDao student2 = new StudentDao("Jane Doe", "duplicate@example.com");

        // Act: Create first student
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student1)))
                .andExpect(status().isCreated());

        // Act: Try to create student with duplicate email
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student2)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void createStudentWithInvalidData_ShouldReturnBadRequest() throws Exception {
        // Test empty name
        StudentDao invalidStudent1 = new StudentDao("", "test@example.com");
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidStudent1)))
                .andExpect(status().isBadRequest());

        // Test empty email
        StudentDao invalidStudent2 = new StudentDao("John Doe", "");
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidStudent2)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getStudents_WhenEmpty_ShouldReturnEmptyArray() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}