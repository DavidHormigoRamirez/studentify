package es.fpalanturing.studentify.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Model representing health status information for the API.
 * Used to provide system health check responses.
 */
public class Health {

    private static final DateTimeFormatter TIMESTAMP_FORMATTER = 
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private String status;
    private String message;
    private String timestamp;

    /**
     * Constructor to create a Health object with status and message.
     * Automatically sets the current timestamp.
     * @param status the health status (e.g., "success", "error")
     * @param message descriptive message about the health status
     */
    public Health(String status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
    }

    /**
     * Gets the timestamp when the health check was performed.
     * @return ISO formatted timestamp string
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the health status.
     * @return the status string
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the health status.
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the health message.
     * @return the descriptive message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the health message.
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
