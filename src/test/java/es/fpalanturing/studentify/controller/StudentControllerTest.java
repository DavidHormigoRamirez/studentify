package es.fpalanturing.studentify.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.fpalanturing.studentify.model.Student;
import es.fpalanturing.studentify.model.StudentDao;
import es.fpalanturing.studentify.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for StudentController.
 * Tests REST endpoints for student operations.
 */
@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Student testStudent;
    private StudentDao testStudentDao;

    @BeforeEach
    void setUp() {
        testStudent = new Student("John Doe", "john.doe@example.com");
        testStudent.setId(1L);
        testStudentDao = new StudentDao("John Doe", "john.doe@example.com");
    }

    @Test
    void getAllStudents_ShouldReturnListOfStudents() throws Exception {
        // Arrange
        when(studentService.readAllStudents()).thenReturn(Arrays.asList(testStudent));

        // Act & Assert
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"));

        verify(studentService).readAllStudents();
    }

    @Test
    void getAllStudents_ShouldReturnEmptyList_WhenNoStudents() throws Exception {
        // Arrange
        when(studentService.readAllStudents()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());

        verify(studentService).readAllStudents();
    }

    @Test
    void getAllStudents_ShouldReturnInternalServerError_WhenServiceThrowsException() throws Exception {
        // Arrange
        when(studentService.readAllStudents()).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isInternalServerError());

        verify(studentService).readAllStudents();
    }

    @Test
    void createStudent_ShouldReturnCreatedStudent() throws Exception {
        // Arrange
        when(studentService.createStudent(any(Student.class))).thenReturn(testStudent);

        // Act & Assert
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testStudentDao)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(studentService).createStudent(any(Student.class));
    }

    @Test
    void createStudent_ShouldReturnBadRequest_WhenStudentDaoIsNull() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("null"))
                .andExpect(status().isBadRequest());

        verify(studentService, never()).createStudent(any(Student.class));
    }

    @Test
    void createStudent_ShouldReturnBadRequest_WhenNameIsNull() throws Exception {
        // Arrange
        StudentDao invalidDao = new StudentDao(null, "test@example.com");

        // Act & Assert
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDao)))
                .andExpect(status().isBadRequest());

        verify(studentService, never()).createStudent(any(Student.class));
    }

    @Test
    void createStudent_ShouldReturnBadRequest_WhenEmailIsNull() throws Exception {
        // Arrange
        StudentDao invalidDao = new StudentDao("John Doe", null);

        // Act & Assert
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDao)))
                .andExpect(status().isBadRequest());

        verify(studentService, never()).createStudent(any(Student.class));
    }

    @Test
    void createStudent_ShouldReturnBadRequest_WhenNameIsEmpty() throws Exception {
        // Arrange
        StudentDao invalidDao = new StudentDao("", "test@example.com");

        // Act & Assert
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDao)))
                .andExpect(status().isBadRequest());

        verify(studentService, never()).createStudent(any(Student.class));
    }

    @Test
    void createStudent_ShouldReturnBadRequest_WhenEmailIsEmpty() throws Exception {
        // Arrange
        StudentDao invalidDao = new StudentDao("John Doe", "");

        // Act & Assert
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDao)))
                .andExpect(status().isBadRequest());

        verify(studentService, never()).createStudent(any(Student.class));
    }

    @Test
    void createStudent_ShouldTrimWhitespace() throws Exception {
        // Arrange
        StudentDao daoWithWhitespace = new StudentDao("  John Doe  ", "  john.doe@example.com  ");
        when(studentService.createStudent(any(Student.class))).thenReturn(testStudent);

        // Act & Assert
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(daoWithWhitespace)))
                .andExpect(status().isCreated());

        verify(studentService).createStudent(argThat(student ->
                "John Doe".equals(student.getName()) &&
                        "john.doe@example.com".equals(student.getEmail())
        ));
    }

    @Test
    void createStudent_ShouldReturnInternalServerError_WhenServiceThrowsException() throws Exception {
        // Arrange
        when(studentService.createStudent(any(Student.class)))
                .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testStudentDao)))
                .andExpect(status().isInternalServerError());

        verify(studentService).createStudent(any(Student.class));
    }
}