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

@Service
public class PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;

    @Transactional
    public Property createProperty(Property property) {
        return propertyRepository.save(property);
    }

    public List<Property> findPropertiesByPriceRange(BigDecimal min, BigDecimal max) {
        return propertyRepository.findByPriceBetween(min, max);
    }
    
    public List<Property> findAll() {
    	return propertyRepository.findAll();
    }

    public Optional<Property> findPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

    @Transactional
    public Property updatePropertyStatus(Long id, PropertyStatus status) {
        Property property = propertyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Property not found"));
        property.setStatus(status);
        return propertyRepository.save(property);
    }

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

	public void deleteProperty(Long id) {
		propertyRepository.deleteById(id);
		
	}
}