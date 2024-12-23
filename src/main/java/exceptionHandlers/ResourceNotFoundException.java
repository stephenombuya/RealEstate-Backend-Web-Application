package com.realestate.app.exceptionHandlers;

/**
 * Custom exception to be thrown when a requested resource is not found.
 * Extends RuntimeException to allow unchecked exception handling.
 */
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * @param message the detail message, which is saved for later retrieval by the {@link Throwable#getMessage()} method.
     */
    public ResourceNotFoundException(String message) {
        super(message);  // Pass the message to the superclass constructor (RuntimeException).
    }
}
