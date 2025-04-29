package com.realestate.app.controllers;

import java.net.URI;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.realestate.app.models.Listing;
import com.realestate.app.services.ListingService;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

/**
 * Controller class for handling CRUD operations related to real estate listings.
 */
@RestController
@RequestMapping("/api/listings")
public class ListingController {

    @Autowired
    private ListingService listingService;

    /**
     * Creates a new real estate listing.
     */
    @PostMapping
    @RateLimiter(name = "writeOperations", fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<Listing> createListing(@Validated @RequestBody Listing listing) {
        Listing createdListing = listingService.createListing(listing);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdListing.getId())
            .toUri();

        return ResponseEntity.created(location).body(createdListing);
    }

    /**
     * Retrieves all real estate listings.
     */
    @GetMapping
    @RateLimiter(name = "searchOperations", fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<List<Listing>> getAllListings() {
        List<Listing> listings = listingService.getAllListings();
        return listings.isEmpty() 
            ? ResponseEntity.noContent().build() 
            : ResponseEntity.ok(listings);
    }

    /**
     * Retrieves all featured listings.
     */
    @GetMapping("/featured")
    @RateLimiter(name = "searchOperations", fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<List<Listing>> getFeaturedListings() {
        List<Listing> featuredListings = listingService.findFeaturedListings();
        return featuredListings.isEmpty() 
            ? ResponseEntity.noContent().build() 
            : ResponseEntity.ok(featuredListings);
    }

    /**
     * Retrieves a real estate listing by its ID.
     */
    @GetMapping("/{id}")
    @RateLimiter(name = "standardApi", fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<Listing> getListingById(@PathVariable Long id) {
        try {
            Listing listing = listingService.findListingById(id);
            return ResponseEntity.ok(listing);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Updates a real estate listing by its ID.
     */
    @PutMapping("/{id}")
    @RateLimiter(name = "writeOperations", fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<Listing> updateListing(
        @PathVariable Long id, 
        @Validated @RequestBody Listing listingDetails
    ) {
        try {
            Listing updatedListing = listingService.updateListing(id, listingDetails);
            return ResponseEntity.ok(updatedListing);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Deletes a real estate listing by its ID.
     */
    @DeleteMapping("/{id}")
    @RateLimiter(name = "criticalOperations", fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        try {
            listingService.deleteListing(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    /**
     * Fallback method for rate-limited endpoints
     */
    public ResponseEntity<Object> rateLimiterFallback(Exception ex) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body("Rate limit exceeded. Please try again later.");
    }
}
