package com.realestate.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.app.models.JwtResponse;
import com.realestate.app.models.User;
import com.realestate.app.repositories.UserRepository;
import com.realestate.app.security.JWTUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
    private final AuthenticationManager authenticationManager;
    
	@Autowired
	private final PasswordEncoder passwordEncoder;
    
	@Autowired
	private final UserRepository userRepository;
	
	@Autowired
	private JWTUtil jWTUtil;

    public AuthController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
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


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User userRequest) {
        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already taken");
        }

        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRequest.setRoles(userRequest.getRoles()); // Assign default role
        userRepository.save(userRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
}

