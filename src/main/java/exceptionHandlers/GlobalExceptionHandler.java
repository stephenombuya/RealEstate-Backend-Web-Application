package com.realestate.app.exceptionHandlers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler that handles various types of exceptions across the application.
 * This class intercepts exceptions thrown by controllers and returns appropriate HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation exceptions, which occur when a request fails validation.
     * This method processes the validation errors and returns them in the response body.
     *
     * @param ex the MethodArgumentNotValidException that contains the validation errors.
     * @return a ResponseEntity with a map of validation error messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();  // Get the name of the field that failed validation.
            String errorMessage = error.getDefaultMessage();  // Get the error message for the field.
            errors.put(fieldName, errorMessage);  // Store the field name and error message in the map.
        });
        return ResponseEntity.badRequest().body(errors);  // Return a bad request response with the validation errors.
    }

    /**
     * Handles ResourceNotFoundException, which is thrown when a requested resource is not found.
     * It returns a 404 Not Found response with the exception message.
     *
     * @param ex the ResourceNotFoundException that was thrown.
     * @return a ResponseEntity with the exception message and a 404 status.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());  // Return a 404 response with the message.
    }

    /**
     * Handles general exceptions that do not fall into specific categories.
     * It returns a 500 Internal Server Error response with a generic error message.
     *
     * @param ex the general exception that was thrown.
     * @return a ResponseEntity with a generic error message and a 500 status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An unexpected error occurred: " + ex.getMessage());  // Return a 500 response with a generic error message.
    }
}
