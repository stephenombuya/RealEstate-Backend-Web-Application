package com.realestate.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.app.models.Listing;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByIsFeaturedTrue();
    List<Listing> findByAgent_Id(Long agentId);
}
