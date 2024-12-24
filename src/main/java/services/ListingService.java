package com.realestate.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.app.models.Listing;
import com.realestate.app.repositories.ListingRepository;

import jakarta.transaction.Transactional;

/**
 * Service class that provides CRUD operations for {@link Listing} entities.
 * It interacts with the {@link ListingRepository} to perform actions on listings.
 */
@Service
public class ListingService {
    @Autowired
    private ListingRepository listingRepository;

    /**
     * Creates and saves a new listing.
     *
     * @param listing the listing to be created
     * @return the saved listing entity
     */
    @Transactional
    public Listing createListing(Listing listing) {
        return listingRepository.save(listing);
    }

    /**
     * Retrieves a list of featured listings.
     *
     * @return a list of featured listings
     */
    public List<Listing> findFeaturedListings() {
        return listingRepository.findByIsFeaturedTrue();
    }

    /**
     * Retrieves all listings.
     *
     * @return a list of all listings
     */
    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    /**
     * Retrieves a listing by its ID.
     *
     * @param id the ID of the listing
     * @return the found listing
     * @throws RuntimeException if no listing is found with the given ID
     */
    public Listing findListingById(Long id) {
        Optional<Listing> listing = listingRepository.findById(id);
        return listing.orElseThrow(() -> new RuntimeException("Listing not found with id: " + id));
    }

    /**
     * Updates an existing listing with new details.
     *
     * @param id the ID of the listing to be updated
     * @param updatedListing the updated listing details
     * @return the updated listing entity
     */
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

    /**
     * Deletes a listing by its ID.
     *
     * @param id the ID of the listing to be deleted
     * @throws RuntimeException if no listing is found with the given ID
     */
    @Transactional
    public void deleteListing(Long id) {
        Listing listing = findListingById(id);
        listingRepository.delete(listing);
    }
}
