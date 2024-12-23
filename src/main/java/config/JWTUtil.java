package com.realestate.app.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    // Secret key used to sign JWT. Should be stored securely (e.g., environment variable).
    private final String SECRET_KEY = "YourSecureAndLongSecretKeyHere12345678901234567890";
    
    // Expiration time for the token (1 hour)
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    /**
     * Generates a JWT token based on the provided username.
     *
     * @param username The username to be set as the subject of the token.
     * @return The generated JWT token.
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * Validates the JWT token by checking its integrity and signature.
     *
     * @param token The JWT token to validate.
     * @return true if the token is valid, false otherwise.
     */
    public boolean validateToken(String token) {
        try {
            // Parse the token to ensure it's valid
            Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token);
            return true;  // Token is valid
        } catch (Exception e) {
            // Log the error (optional)
            return false;  // Token is invalid
        }
    }

    /**
     * Extracts the username from the JWT token.
     *
     * @param token The JWT token from which to extract the username.
     * @return The username (subject) from the token.
     */
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * Extracts claims (like expiration date, subject, etc.) from the JWT token.
     *
     * @param token The JWT token from which to extract claims.
     * @return The claims contained within the token.
     */
    private Claims extractClaims(String token) {
        // Parse the token to get the claims
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if the JWT token has expired.
     *
     * @param token The JWT token to check.
     * @return true if the token has expired, false otherwise.
     */
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
