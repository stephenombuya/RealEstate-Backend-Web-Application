package com.realestate.app.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity class representing an agent in the real estate system.
 * This class maps to the "agents" table in the database.
 */
@Entity
@Table(name = "agents")
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;
    
    private String bio;

    @Column(unique = true, nullable = false)
    private String licenseNumber;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    /**
     * Gets the ID of the agent.
     * 
     * @return the ID of the agent.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the agent.
     * 
     * @param id the ID of the agent.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the first name of the agent.
     * 
     * @return the first name of the agent.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the agent.
     * 
     * @param firstName the first name of the agent.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the agent.
     * 
     * @return the last name of the agent.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the agent.
     * 
     * @param lastName the last name of the agent.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email of the agent.
     * 
     * @return the email of the agent.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the agent.
     * 
     * @param email the email of the agent.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number of the agent.
     * 
     * @return the phone number of the agent.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the agent.
     * 
     * @param phone the phone number of the agent.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the biography of the agent.
     * 
     * @return the biography of the agent.
     */
    public String getBio() {
        return bio;
    }

    /**
     * Sets the biography of the agent.
     * 
     * @param bio the biography of the agent.
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Gets the license number of the agent.
     * 
     * @return the license number of the agent.
     */
    public String getLicenseNumber() {
        return licenseNumber;
    }

    /**
     * Sets the license number of the agent.
     * 
     * @param licenseNumber the license number of the agent.
     */
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    /**
     * Gets the creation timestamp of the agent record.
     * 
     * @return the creation timestamp of the agent record.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp of the agent record.
     * 
     * @param createdAt the creation timestamp of the agent record.
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns a string representation of the Agent object.
     * 
     * @return a string representation of the Agent object.
     */
    @Override
    public String toString() {
        return "Agent [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
                + ", phone=" + phone + ", bio=" + bio + ", licenseNumber=" + licenseNumber + ", createdAt=" + createdAt
                + "]";
    }
}
