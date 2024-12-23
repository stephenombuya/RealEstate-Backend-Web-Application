package com.realestate.app.config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JwtAuthenticationFilter is responsible for validating and authenticating JWT tokens
 * from incoming HTTP requests. It ensures that requests containing valid tokens 
 * are properly authenticated and that the authentication context is set.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private final JWTUtil jWTUtil;

    /**
     * Constructor for the JwtAuthenticationFilter.
     *
     * @param jWTUtil JWT utility class to handle token validation and extraction.
     */
    public JwtAuthenticationFilter(JWTUtil jWTUtil) {
        this.jWTUtil = jWTUtil;
    }

    /**
     * Filters the incoming request to check for JWT in the Authorization header.
     * If the token is valid, it sets the authentication in the security context.
     *
     * @param request The HTTP request.
     * @param response The HTTP response.
     * @param filterChain The filter chain to continue processing the request.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
        throws ServletException, IOException {
        
        String token = getTokenFromRequest(request);
        
        // If a token exists and is valid, authenticate the user
        if (token != null && jWTUtil.validateToken(token)) {
            String username = jWTUtil.extractUsername(token);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                username, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        
        // Proceed with the filter chain
        try {
            filterChain.doFilter(request, response);
        } catch (IOException | ServletException | java.io.IOException e) {
            // Log and handle any exception during the filtering process
            e.printStackTrace();  // Replace with proper logging
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Internal server error status
        }
    }

    /**
     * Retrieves the JWT token from the Authorization header of the HTTP request.
     *
     * @param request The HTTP request.
     * @return The JWT token, or null if not found.
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // Extract token part of "Bearer <token>"
        }
        return null;
    }
}
