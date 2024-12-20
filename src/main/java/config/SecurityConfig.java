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

import com.realestate.app.security.JWTUtil;

@Configuration
public class SecurityConfig {

	@Autowired
	private final JWTUtil jWTUtil;

	public SecurityConfig(JWTUtil jWTUtil) {
		this.jWTUtil = jWTUtil;
	}
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/register", "/auth/login", "/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jWTUtil), UsernamePasswordAuthenticationFilter.class)
            )
            .csrf().disable()
            .exceptionHandling(exception -> exception
                .defaultAuthenticationEntryPointFor(
                    new Http403ForbiddenEntryPoint(), // Returns 403 for AJAX requests
                    new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest")
                )
                .defaultAuthenticationEntryPointFor(
                    new BasicAuthenticationEntryPoint(), // Default for non-AJAX requests
                    request -> true // Match all other requests
                )
            )
            .httpBasic()
            .and()
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
            );
        return http.build();
    }
}
