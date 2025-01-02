package com.realestate.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.app.config.JWTUtil;
import com.realestate.app.models.JwtResponse;
import com.realestate.app.models.User;
import com.realestate.app.repositories.UserRepository;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller class for handling user authentication and registration.
 */
@CrossOrigin(origins = "https://4309afec-5f84-481c-8c38-a19b62503621.lovableproject.com")
@RestController
@RequestMapping("/auth")
@Tag(name = "AuthController", description = "Operations related to Authenticating Users")
public class AuthController {

    @Autowired
    private final AuthenticationManager authenticationManager;
    
    @Autowired
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    private final UserRepository userRepository;
    
    @Autowired
    private JWTUtil jWTUtil;

    public AuthController(AuthenticationManager authenticationManager, 
                PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    /**
     * Handles user login by validating username and password, 
     * and generating a JWT token if successful.
     * 
     * @param loginRequest The user's login request containing username and password.
     * @return A ResponseEntity containing the JWT token if login is successful, 
     *         or an Unauthorized status if login fails.
     */
    @PostMapping("/login")
    @Operation(summary = "Login a User by their username and password", 
        description = "Logins a user by their unique username and password")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "401", description = "Invalid username or password")
    })
    @RateLimiter(name = "loginRateLimiter", fallbackMethod = "loginFallback")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        try {
            // Authenticate the user using the AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );

            // Check if authentication was successful
            if (authentication.isAuthenticated()) {
                // Generate the token using the username from loginRequest
                String token = jWTUtil.generateToken(loginRequest.getUsername());

                // Return the token as part of the response
                return ResponseEntity.ok(new JwtResponse(token));
            } else {
                // Authentication failed
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }
        } catch (Exception e) {
            // Catch and log any errors that occur during authentication
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    /**
     * Registers a new user with the provided details.
     * 
     * @param userRequest The user's registration details containing username, password, and roles.
     * @return A ResponseEntity indicating the success or failure of the registration.
     */
    @PostMapping("/register")
    @Operation(summary = "Involved in user registration", 
                description = "Users are registered using their unique username, password, and roles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "Username already taken")
    })
    @RateLimiter(name = "registerRateLimiter", fallbackMethod = "registerFallback")
    public ResponseEntity<?> register(@RequestBody User userRequest) {
        try {
            // Check if the username is already taken
            if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already taken");
            }

            // Encode the password and set the default role
            userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            userRequest.setRoles(userRequest.getRoles()); // Assign default role
            userRepository.save(userRequest);

            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (Exception e) {
            // Catch and log any errors during registration
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration");
        }
    }
    
 // Fallback methods for rate limiting
    public ResponseEntity<?> loginFallback(User loginRequest, Throwable t) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many login attempts. Please try again later.");
    }

    public ResponseEntity<?> registerFallback(User userRequest, Throwable t) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many registration attempts. Please try again later.");
    }
}
