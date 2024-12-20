package com.realestate.app.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    @Autowired
    private ListingService listingService;

    // Create a new listing
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

    // Get all listings
    @GetMapping
    public ResponseEntity<List<Listing>> getAllListings() {
        List<Listing> listings = listingService.getAllListings();
        return listings.isEmpty() 
            ? ResponseEntity.noContent().build() 
            : ResponseEntity.ok(listings);
    }

    // Get featured listings
    @GetMapping("/featured")
    public ResponseEntity<List<Listing>> getFeaturedListings() {
        List<Listing> featuredListings = listingService.findFeaturedListings();
        return featuredListings.isEmpty() 
            ? ResponseEntity.noContent().build() 
            : ResponseEntity.ok(featuredListings);
    }

    // Get listing by ID
    @GetMapping("/{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable Long id) {
        try {
            Listing listing = listingService.findListingById(id);
            return ResponseEntity.ok(listing);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Update a listing
    @PutMapping("/{id}")
    public ResponseEntity<Listing> updateListing(
        @PathVariable Long id, 
        @Validated @RequestBody Listing listingDetails
    ) {
        try {
            Listing updatedListing = listingService.updateListing(id, listingDetails);
            return ResponseEntity.ok(updatedListing);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a listing
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        try {
            listingService.deleteListing(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
