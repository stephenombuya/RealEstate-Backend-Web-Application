package com.realestate.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures Cross-Origin Resource Sharing (CORS) for the application.
 * This class implements the WebMvcConfigurer interface to customize the
 * CORS settings for the application, allowing certain domains and HTTP methods
 * to interact with the API.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures the CORS mappings for the application.
     * This method specifies which origins, HTTP methods, and headers are allowed
     * for cross-origin requests, as well as enabling credentials to be sent with requests.
     *
     * @param registry the CorsRegistry used to configure the CORS mappings.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Apply CORS to all endpoints.
            .allowedOriginPatterns("https://*.yourdomain.com")  // Allow requests from any subdomain of "yourdomain.com" (replace with your actual domain in production).
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Allow these HTTP methods.
            .allowedHeaders("*")  // Allow all headers in requests.
            .allowCredentials(true);  // Allow credentials (cookies, HTTP authentication) to be sent.
    }
}
