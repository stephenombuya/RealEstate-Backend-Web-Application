package com.realestate.app.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realestate.app.models.Property;
import com.realestate.app.models.Property.PropertyType;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    /**
     * Retrieves properties within a specified price range.
     * 
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @return a list of properties within the specified price range
     */
    List<Property> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * Retrieves properties within a specified price range with pagination.
     * 
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @param pageable the pageable object containing page and sort information
     * @return a page of properties within the specified price range
     */
    Page<Property> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    /**
     * Retrieves properties based on type and city.
     * 
     * @param type the type of property
     * @param city the city where the property is located
     * @return a list of properties matching the type and city
     */
    List<Property> findByPropertyTypeAndCity(PropertyType type, String city);

    /**
     * Retrieves properties based on type and city with pagination.
     * 
     * @param type the type of property
     * @param city the city where the property is located
     * @param pageable the pageable object containing page and sort information
     * @return a page of properties matching the type and city
     */
    Page<Property> findByPropertyTypeAndCity(PropertyType type, String city, Pageable pageable);

    /**
     * Retrieves properties with a minimum number of bedrooms.
     * 
     * @param bedrooms the minimum number of bedrooms
     * @return a list of properties with at least the specified number of bedrooms
     */
    List<Property> findByBedroomsGreaterThanEqual(Integer bedrooms);

    /**
     * Retrieves properties with a minimum number of bedrooms with pagination.
     * 
     * @param bedrooms the minimum number of bedrooms
     * @param pageable the pageable object containing page and sort information
     * @return a page of properties with at least the specified number of bedrooms
     */
    Page<Property> findByBedroomsGreaterThanEqual(Integer bedrooms, Pageable pageable);

    /**
     * Retrieves properties with at least the specified number of bathrooms.
     * 
     * @param bathrooms the minimum number of bathrooms
     * @return a list of properties with at least the specified number of bathrooms
     */
    List<Property> findByBathroomsGreaterThanEqual(Double bathrooms);

    /**
     * Retrieves properties with square footage within a range.
     * 
     * @param minSquareFeet the minimum square footage
     * @param maxSquareFeet the maximum square footage
     * @return a list of properties within the specified square footage range
     */
    List<Property> findBySquareFeetBetween(Integer minSquareFeet, Integer maxSquareFeet);

    /**
     * Custom query to retrieve properties by type and minimum bedrooms and bathrooms.
     * 
     * @param type the type of property
     * @param minBedrooms the minimum number of bedrooms
     * @param minBathrooms the minimum number of bathrooms
     * @return a list of properties matching the criteria
     */
    @Query("SELECT p FROM Property p WHERE p.propertyType = :type AND p.bedrooms >= :minBedrooms AND p.bathrooms >= :minBathrooms")
    List<Property> findPropertiesByTypeAndMinBedroomsAndBathrooms(
        @Param("type") PropertyType type, 
        @Param("minBedrooms") Integer minBedrooms, 
        @Param("minBathrooms") Double minBathrooms
    );
}
