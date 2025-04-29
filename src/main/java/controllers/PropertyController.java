package com.realestate.app.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.app.models.Property;
import com.realestate.app.services.PropertyService;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

/**
 * Controller class for handling CRUD operations related to properties.
 */
@RestController
@RequestMapping("/api/properties")
public class PropertyController {
    
    @Autowired
    private PropertyService propertyService;

    /**
     * Creates a new property.
     * 
     * @param property The property to be created.
     * @return A ResponseEntity containing the created property and the location of the new resource.
     */
    @PostMapping
    @RateLimiter(name = "writeOperations", fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<Property> createProperty(@Validated @RequestBody Property property) {
        Property createdProperty = propertyService.createProperty(property);
        return new ResponseEntity<>(createdProperty, HttpStatus.CREATED);
    }

    /**
     * Retrieves all properties.
     * 
     * @return A ResponseEntity containing a list of all properties.
     */
    @GetMapping
    @RateLimiter(name = "searchOperations", fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<List<Property>> findAllProperties() {
        List<Property> properties = propertyService.findAll();
        return properties.isEmpty() 
            ? ResponseEntity.noContent().build() 
            : ResponseEntity.ok(properties);
    }

    /**
     * Retrieves a property by its ID.
     * 
     * @param id The ID of the property to be retrieved.
     * @return A ResponseEntity containing the property if found, or a Not Found status if not found.
     */
    @GetMapping("/{id}")
    @RateLimiter(name = "standardApi", fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<Property> getProperty(@PathVariable Long id) {
        return propertyService.findPropertyById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Searches for properties based on the provided price range.
     * 
     * @param minPrice The minimum price for the search (optional).
     * @param maxPrice The maximum price for the search (optional).
     * @return A ResponseEntity containing a list of properties within the price range.
     */
    @GetMapping("/search")
    @RateLimiter(name = "searchOperations", fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<List<Property>> searchProperties(
        @RequestParam(required = false) BigDecimal minPrice,
        @RequestParam(required = false) BigDecimal maxPrice
    ) {
        List<Property> properties = propertyService.findPropertiesByPriceRange(minPrice, maxPrice);
        return properties.isEmpty() 
            ? ResponseEntity.noContent().build() 
            : ResponseEntity.ok(properties);
    }

    /**
     * Updates a property by its ID.
     * 
     * @param id The ID of the property to be updated.
     * @param property The updated property information.
     * @return A ResponseEntity containing the updated property.
     * @throws Exception If the property cannot be updated.
     */
    @PutMapping("/{id}")
    @RateLimiter(name = "writeOperations", fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<Property> updateProperty(@PathVariable Long id, @Validated @RequestBody Property property) throws Exception {
        try {
            Property updatedProperty = propertyService.updateProperty(id, property);
            return new ResponseEntity<>(updatedProperty, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Deletes a property by its ID.
     * 
     * @param id The ID of the property to be deleted.
     * @return A ResponseEntity indicating the result of the delete operation.
     */
    @DeleteMapping("/{id}")
    @RateLimiter(name = "criticalOperations", fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        try {
            propertyService.deleteProperty(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
	/*
	 * 
	 * Fallback method for all rate-limited endpoints
	 */
	 public ResponseEntity<?> rateLimiterFallback(Throwable t) {
	     return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
	         .body("Too many requests. Please slow down and try again.");
	 }
}
