package com.realestate.app.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realestate.app.models.Listing;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    /**
     * Retrieves all featured listings.
     * 
     * @return a list of featured listings
     */
    List<Listing> findByIsFeaturedTrue();

    /**
     * Retrieves featured listings with pagination.
     * 
     * @param pageable the pageable object containing page and sort information
     * @return a page of featured listings
     */
    Page<Listing> findByIsFeaturedTrue(Pageable pageable);

    /**
     * Retrieves listings associated with a specific agent.
     * 
     * @param agentId the ID of the agent
     * @return a list of listings for the specified agent
     */
    List<Listing> findByAgent_Id(Long agentId);

    // Check if a listing exists for the given agentId
    boolean existsByAgentId(Long agentId);

    /**
     * Retrieves listings associated with a specific agent with pagination.
     * 
     * @param agentId the ID of the agent
     * @param pageable the pageable object containing page and sort information
     * @return a page of listings for the specified agent
     */
    Page<Listing> findByAgent_Id(Long agentId, Pageable pageable);

    /**
     * Retrieves featured listings within a specific price range.
     * 
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @return a list of featured listings within the specified price range
     */
    @Query("SELECT l FROM Listing l WHERE l.isFeatured = true AND l.listingPrice BETWEEN :minPrice AND :maxPrice")
    List<Listing> findFeaturedListingsByPriceRange(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);

    /**
     * Retrieves listings for a specific property.
     * 
     * @param propertyId the ID of the property
     * @return a list of listings for the specified property
     */
    List<Listing> findByProperty_Id(Long propertyId);
}
