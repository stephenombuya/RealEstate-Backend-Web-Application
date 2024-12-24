package com.realestate.app.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a property listed in the real estate application.
 * Includes details such as title, description, price, type, status, and location.
 */
@Entity
@Table(name = "properties")
public class Property {

    /**
     * The unique identifier for the property.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The title of the property.
     */
    @Column(nullable = false)
    private String title;

    /**
     * A detailed description of the property.
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * The price of the property.
     */
    @Column(nullable = false)
    private BigDecimal price;

    /**
     * The type of the property (e.g., Residential, Commercial, Land).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyType propertyType;

    /**
     * The current status of the property (e.g., Available, Sold, Pending).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyStatus status;

    /**
     * Enumeration for the type of property.
     */
    public enum PropertyType {
        RESIDENTIAL,
        COMMERCIAL,
        LAND
    }

    /**
     * Enumeration for the status of the property.
     */
    public enum PropertyStatus {
        AVAILABLE,
        SOLD,
        PENDING
    }

    /**
     * The square footage of the property.
     */
    private Integer squareFeet;

    /**
     * The number of bedrooms in the property.
     */
    private Integer bedrooms;

    /**
     * The number of bathrooms in the property.
     */
    private Double bathrooms;

    /**
     * The address of the property.
     */
    @Column(nullable = false)
    private String address;

    /**
     * The city where the property is located.
     */
    @Column(nullable = false)
    private String city;

    /**
     * The state where the property is located.
     */
    @Column(nullable = false)
    private String state;

    /**
     * The ZIP code of the property location.
     */
    @Column(nullable = false)
    private String zipCode;

    /**
     * The latitude of the property location.
     */
    private Double latitude;

    /**
     * The longitude of the property location.
     */
    private Double longitude;

    /**
     * The timestamp when the property was created.
     */
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /**
     * The timestamp when the property was last updated.
     */
    private LocalDateTime updatedAt;

    // Getters and Setters

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}

	public PropertyStatus getStatus() {
		return status;
	}

	public void setStatus(PropertyStatus status) {
		this.status = status;
	}

	public Integer getSquareFeet() {
		return squareFeet;
	}

	public void setSquareFeet(Integer squareFeet) {
		this.squareFeet = squareFeet;
	}

	public Integer getBedrooms() {
		return bedrooms;
	}

	public void setBedrooms(Integer bedrooms) {
		this.bedrooms = bedrooms;
	}

	public Double getBathrooms() {
		return bathrooms;
	}

	public void setBathrooms(Double bathrooms) {
		this.bathrooms = bathrooms;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
    // Other methods (e.g., toString)

    @Override
    public String toString() {
        return "Property [id=" + id + ", title=" + title + ", description=" + description + ", price=" + price
                + ", propertyType=" + propertyType + ", status=" + status + ", squareFeet=" + squareFeet + ", bedrooms="
                + bedrooms + ", bathrooms=" + bathrooms + ", address=" + address + ", city=" + city + ", state=" + state
                + ", zipCode=" + zipCode + ", latitude=" + latitude + ", longitude=" + longitude + ", createdAt="
                + createdAt + ", updatedAt=" + updatedAt + "]";
    }
}
