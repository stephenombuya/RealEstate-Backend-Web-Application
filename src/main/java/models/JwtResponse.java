package com.realestate.app.models;

/**
 * Represents the response containing the JWT token.
 * This is typically used when sending the token back to the client after successful authentication.
 */
public class JwtResponse {
    
    /**
     * The JWT token that is generated after authentication.
     */
    private String token;

    /**
     * Constructs a new JwtResponse with the provided token.
     * 
     * @param token the JWT token to be included in the response.
     */
    public JwtResponse(String token) {
        this.token = token;
    }

    /**
     * Gets the JWT token.
     * 
     * @return the JWT token.
     */
    public String getToken() {
        return token;
    }
    
    /**
     * Sets the JWT token.
     * 
     * @param token the JWT token to set.
     */
    public void setToken(String token) {
        this.token = token;
    }
}
