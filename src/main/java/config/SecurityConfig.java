package com.realestate.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

@Configuration
public class SecurityConfig {

    // Inject the JWT utility class
    @Autowired
    private final JWTUtil jWTUtil;

    public SecurityConfig(JWTUtil jWTUtil) {
        this.jWTUtil = jWTUtil;
    }
    
    /**
     * Bean for password encoding using BCrypt.
     *
     * @return PasswordEncoder instance for password encoding.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean for AuthenticationManager used for authenticating requests.
     *
     * @param config AuthenticationConfiguration to build the manager.
     * @return AuthenticationManager instance.
     * @throws Exception If any error occurs during configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Configures HTTP security, including endpoints, filters, and exception handling.
     *
     * @param http HttpSecurity instance used for configuring HTTP security.
     * @return SecurityFilterChain with configured security settings.
     * @throws Exception If any error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/register", "/auth/login", "/css/**", "/js/**", "/images/**").permitAll()  // Allow unauthenticated access to specific routes
                .anyRequest().authenticated()  // All other requests require authentication
            )
            .csrf().disable()  // Disable CSRF protection (may want to enable for certain use cases)
            .exceptionHandling(exception -> exception
                .defaultAuthenticationEntryPointFor(
                    new Http403ForbiddenEntryPoint(), // Return 403 for AJAX requests
                    new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest")
                )
                .defaultAuthenticationEntryPointFor(
                    new BasicAuthenticationEntryPoint(), // Default entry point for non-AJAX requests
                    request -> true // Match all other requests
                )
            )
            .httpBasic()  // Enable basic HTTP authentication
            .and()
            .logout(logout -> logout
                .logoutUrl("/logout")  // Specify logout URL
                .logoutSuccessUrl("/login?logout")  // Redirect to login page after logout
                .invalidateHttpSession(true)  // Invalidate the session upon logout
            )
            .addFilterBefore(new JwtAuthenticationFilter(jWTUtil), UsernamePasswordAuthenticationFilter.class);  // Add JWT filter before the authentication filter
        return http.build();
    }
}
