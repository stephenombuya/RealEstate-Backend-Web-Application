package com.realestate.app.models;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents a user in the system, integrating with Spring Security.
 */
@Entity
public class User implements UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The username of the user. Must be unique.
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * The password of the user.
     */
    @Column(nullable = false)
    private String password;

    /**
     * The roles assigned to the user, stored as strings.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = new HashSet<>();

    /**
     * Default constructor for JPA.
     */
    public User() {
    }

    /**
     * Parameterized constructor for creating a user instance.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param roles    the roles assigned to the user
     */
    public User(String username, String password, Set<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    /**
     * Converts roles to authorities for Spring Security.
     *
     * @return a collection of granted authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    /**
     * Indicates whether the user's account is expired.
     *
     * @return true if the account is not expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is locked.
     *
     * @return true if the account is not locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials are expired.
     *
     * @return true if the credentials are not expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled.
     *
     * @return true if the user is enabled
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=[PROTECTED], roles=" + roles + "]";
    }
}
