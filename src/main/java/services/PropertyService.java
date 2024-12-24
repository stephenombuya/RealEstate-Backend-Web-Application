package com.realestate.app.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.app.models.Property;
import com.realestate.app.models.Property.PropertyStatus;
import com.realestate.app.repositories.PropertyRepository;

import jakarta.transaction.Transactional;

/**
 * Service class that provides CRUD operations for {@link Property} entities.
 * It interacts with the {@link PropertyRepository} to perform actions on properties.
 */
@Service
public class PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;

    /**
     * Creates and saves a new property.
     *
     * @param property the property to be created
     * @return the saved property entity
     */
    @Transactional
    public Property createProperty(Property property) {
        return propertyRepository.save(property);
    }

    /**
     * Retrieves properties within a specified price range.
     *
     * @param min the minimum price
     * @param max the maximum price
     * @return a list of properties within the given price range
     */
    public List<Property> findPropertiesByPriceRange(BigDecimal min, BigDecimal max) {
        return propertyRepository.findByPriceBetween(min, max);
    }
    
    /**
     * Retrieves all properties.
     *
     * @return a list of all properties
     */
    public List<Property> findAll() {
    	return propertyRepository.findAll();
    }

    /**
     * Retrieves a property by its ID.
     *
     * @param id the ID of the property
     * @return an {@link Optional} containing the property if found, empty if not
     */
    public Optional<Property> findPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

    /**
     * Updates the status of a property.
     *
     * @param id the ID of the property
     * @param status the new status to set
     * @return the updated property entity
     * @throws RuntimeException if no property is found with the given ID
     */
    @Transactional
    public Property updatePropertyStatus(Long id, PropertyStatus status) {
        Property property = propertyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Property not found"));
        property.setStatus(status);
        return propertyRepository.save(property);
    }

    /**
     * Updates an existing property with new details.
     *
     * @param id the ID of the property to be updated
     * @param property the updated property details
     * @return the updated property entity
     * @throws Exception if no property is found with the given ID
     */
    public Property updateProperty(Long id, Property property) throws Exception {
        // Fetch the property by ID
        Optional<Property> existingPropertyOptional = propertyRepository.findById(id);
        
        if (existingPropertyOptional.isPresent()) {
            // Retrieve the existing property
            Property existingProperty = existingPropertyOptional.get();

            // Update the fields
            existingProperty.setTitle(property.getTitle());
            existingProperty.setDescription(property.getDescription());
            existingProperty.setPrice(property.getPrice());
            existingProperty.setPropertyType(property.getPropertyType());
            existingProperty.setStatus(property.getStatus());
            existingProperty.setSquareFeet(property.getSquareFeet());
            existingProperty.setBedrooms(property.getBedrooms());
            existingProperty.setBathrooms(property.getBathrooms());
            existingProperty.setAddress(property.getAddress());
            existingProperty.setCity(property.getCity());
            existingProperty.setState(property.getState());
            existingProperty.setZipCode(property.getZipCode());
            existingProperty.setLatitude(property.getLatitude());
            existingProperty.setLongitude(property.getLongitude());
            existingProperty.setUpdatedAt(java.time.LocalDateTime.now());

            // Save the updated property to the database
            return propertyRepository.save(existingProperty);
        } else {
            // Handle the case where the property does not exist
            throw new Exception("Property with ID " + id + " not found.");
        }
    }

    /**
     * Deletes a property by its ID.
     *
     * @param id the ID of the property to be deleted
     */
    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }
}
