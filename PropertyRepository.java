package com.realestate.app.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.app.models.Property;
import com.realestate.app.models.Property.PropertyType;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    List<Property> findByPropertyTypeAndCity(PropertyType type, String city);
    List<Property> findByBedroomsGreaterThanEqual(Integer bedrooms);
}
