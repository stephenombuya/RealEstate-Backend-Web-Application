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
     * 
     * @param listing The listing to be created.
     * @return A ResponseEntity containing the created listing and the location of the new resource.
     */
    @PostMapping
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
     * 
     * @return A ResponseEntity containing a list of all listings or a No Content status if empty.
     */
    @GetMapping
    public ResponseEntity<List<Listing>> getAllListings() {
        List<Listing> listings = listingService.getAllListings();
        return listings.isEmpty() 
            ? ResponseEntity.noContent().build() 
            : ResponseEntity.ok(listings);
    }

    /**
     * Retrieves all featured listings.
     * 
     * @return A ResponseEntity containing a list of featured listings or a No Content status if empty.
     */
    @GetMapping("/featured")
    public ResponseEntity<List<Listing>> getFeaturedListings() {
        List<Listing> featuredListings = listingService.findFeaturedListings();
        return featuredListings.isEmpty() 
            ? ResponseEntity.noContent().build() 
            : ResponseEntity.ok(featuredListings);
    }

    /**
     * Retrieves a real estate listing by its ID.
     * 
     * @param id The ID of the listing to be retrieved.
     * @return A ResponseEntity containing the listing if found, or a Not Found status if not found.
     */
    @GetMapping("/{id}")
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
     * 
     * @param id The ID of the listing to be updated.
     * @param listingDetails The new details for the listing.
     * @return A ResponseEntity containing the updated listing if successful, or a Not Found status if the listing doesn't exist.
     */
    @PutMapping("/{id}")
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
     * 
     * @param id The ID of the listing to be deleted.
     * @return A ResponseEntity indicating the result of the delete operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        try {
            listingService.deleteListing(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
