package es.fpalanturing.studentify;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import es.fpalanturing.studentify.model.Health;

/**
 * Unit tests for Health model class.
 * Tests the functionality of health status objects.
 */
@SpringBootTest
public class HealthTests {
    
    private Health health;
    
    @BeforeEach
    void prepareHealth() {
        health = new Health("SUCCESS", "API is healthy");
    }

    @Test
    void testGetMessage() {
        // Arrange
        String expectedMessage = "API is healthy";
        
        // Act
        String actualMessage = health.getMessage();
        
        // Assert
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testGetStatus() {
        // Arrange
        String expectedStatus = "SUCCESS";
        
        // Act
        String actualStatus = health.getStatus();
        
        // Assert
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void testGetTimestamp() {
        // Act
        String timestamp = health.getTimestamp();
        
        // Assert
        assertNotNull(timestamp, "Timestamp should not be null");
        assertFalse(timestamp.isEmpty(), "Timestamp should not be empty");
        assertTrue(timestamp.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}"), 
                "Timestamp should match ISO format pattern");
    }

    @Test
    void testSetMessage() {
        // Arrange
        String expectedMessage = "ERROR";
        
        // Act
        health.setMessage(expectedMessage);
        
        // Assert
        assertEquals(expectedMessage, health.getMessage());
    }

    @Test
    void testSetStatus() {
        // Arrange
        String expectedStatus = "FAILED";
        
        // Act
        health.setStatus(expectedStatus);
        
        // Assert
        assertEquals(expectedStatus, health.getStatus());
    }

    @Test
    void testConstructorWithParameters() {
        // Arrange & Act
        Health newHealth = new Health("ERROR", "System is down");
        
        // Assert
        assertEquals("ERROR", newHealth.getStatus());
        assertEquals("System is down", newHealth.getMessage());
        assertNotNull(newHealth.getTimestamp());
    }

    @Test
    void testTimestampIsGenerated() {
        // Arrange & Act
        Health health1 = new Health("SUCCESS", "Test 1");
        
        // Small delay to ensure different timestamps
        try { Thread.sleep(1); } catch (InterruptedException e) { /* ignore */ }
        
        Health health2 = new Health("SUCCESS", "Test 2");
        
        // Assert
        assertNotEquals(health1.getTimestamp(), health2.getTimestamp(),
                "Different Health objects should have different timestamps");
    }
}
