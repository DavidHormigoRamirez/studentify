package es.fpalanturing.studentify.controller;

import es.fpalanturing.studentify.model.Health;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for health check operations.
 * Provides endpoint to verify API health status.
 */
@RestController
@RequestMapping("/api")
public class HealthController {

    private static final String SUCCESS_STATUS = "success";
    private static final String HEALTHY_MESSAGE = "API is healthy";

    /**
     * Health check endpoint to verify API availability.
     * @return ResponseEntity containing health status information
     */
    @GetMapping("/health")
    public ResponseEntity<Health> getHealthStatus() {
        try {
            Health health = new Health(SUCCESS_STATUS, HEALTHY_MESSAGE);
            return ResponseEntity.ok(health);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
