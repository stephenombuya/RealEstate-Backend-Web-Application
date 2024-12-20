package com.realestate.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.app.models.Listing;
import com.realestate.app.repositories.ListingRepository;

import jakarta.transaction.Transactional;

@Service
public class ListingService {
    @Autowired
    private ListingRepository listingRepository;

    @Transactional
    public Listing createListing(Listing listing) {
        return listingRepository.save(listing);
    }

    public List<Listing> findFeaturedListings() {
        return listingRepository.findByIsFeaturedTrue();
    }

    // Method to get all listings
    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    // Method to find a listing by ID
    public Listing findListingById(Long id) {
        Optional<Listing> listing = listingRepository.findById(id);
        return listing.orElseThrow(() -> new RuntimeException("Listing not found with id: " + id));
    }

    // Method to update a listing
    @Transactional
    public Listing updateListing(Long id, Listing updatedListing) {
        Listing existingListing = findListingById(id);
        existingListing.setProperty(updatedListing.getProperty());
        existingListing.setAgent(updatedListing.getAgent());
        existingListing.setListingDate(updatedListing.getListingDate());
        existingListing.setListingPrice(updatedListing.getListingPrice());
        existingListing.setIsFeatured(updatedListing.getIsFeatured());
        return listingRepository.save(existingListing);
    }

    // Method to delete a listing
    @Transactional
    public void deleteListing(Long id) {
        Listing listing = findListingById(id);
        listingRepository.delete(listing);
    }
}
