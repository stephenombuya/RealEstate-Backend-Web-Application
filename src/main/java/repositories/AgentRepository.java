package com.realestate.app.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.realestate.app.models.Agent;

public interface AgentRepository extends JpaRepository<Agent, Long> {

    /**
     * Finds an agent by their email address.
     * 
     * @param email the email of the agent
     * @return the agent with the specified email
     */
    Agent findByEmail(String email);

    /**
     * Finds agents by their last name.
     * 
     * @param lastName the last name of the agent
     * @return a list of agents with the specified last name
     */
    List<Agent> findByLastName(String lastName);

    /**
     * Finds agents by last name with pagination support.
     * 
     * @param lastName the last name of the agent
     * @param pageable the pageable object containing page and sort information
     * @return a page of agents matching the specified last name
     */
    Page<Agent> findByLastName(String lastName, Pageable pageable);

    /**
     * Searches for agents by a keyword in their first or last name.
     * 
     * @param keyword the search keyword
     * @return a list of agents matching the keyword
     */
    @Query("SELECT a FROM Agent a WHERE a.firstName LIKE %:keyword% OR a.lastName LIKE %:keyword%")
    List<Agent> searchByKeyword(@Param("keyword") String keyword);
}
