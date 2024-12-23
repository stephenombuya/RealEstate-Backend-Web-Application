package com.realestate.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Real Estate Application.
 * This class starts the Spring Boot application by invoking the run method.
 */
@SpringBootApplication
public class RealEstateApplication {

    /**
     * The main method that runs the Spring Boot application.
     * 
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(RealEstateApplication.class, args);
    }
}
