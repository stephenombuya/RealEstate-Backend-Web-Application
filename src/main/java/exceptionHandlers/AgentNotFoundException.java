package com.realestate.app.exceptionHandlers;

/**
 * Custom exception thrown when an agent with a specific identifier is not found.
 * This is a subclass of {@link RuntimeException}, designed to handle cases
 * where the requested agent does not exist in the system.
 */
public class AgentNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	/**
     * Constructs a new AgentNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public AgentNotFoundException(String message) {
        super(message);
    }
}
