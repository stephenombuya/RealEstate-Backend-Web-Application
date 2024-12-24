package com.realestate.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.realestate.app.exceptionHandlers.AgentNotFoundException;
import com.realestate.app.models.Agent;
import com.realestate.app.repositories.AgentRepository;
import com.realestate.app.repositories.ListingRepository;

/**
 * Service class for managing agents in the real estate application.
 * Provides methods to create, retrieve, update, and delete agents.
 */
@Service
public class AgentService {

    private final AgentRepository agentRepository;
	private ListingRepository listingRepository;

    /**
     * Constructor for dependency injection of the AgentRepository, 
     * and the listingRepository
     *
     * @param agentRepository the repository for agent data persistence
     */
    public AgentService(AgentRepository agentRepository, ListingRepository listingRepository) {
        this.agentRepository = agentRepository;
        this.listingRepository = listingRepository;
    }

    /**
     * Creates a new agent and saves it to the database.
     *
     * @param agent the agent to be created
     * @return the created agent
     */
    @Transactional
    public Agent createAgent(Agent agent) {
        return agentRepository.save(agent);
    }

    /**
     * Retrieves an agent by their email address.
     *
     * @param email the email of the agent
     * @return the agent with the specified email, or null if not found
     */
    public Agent findAgentByEmail(String email) {
        return agentRepository.findByEmail(email);
    }

    /**
     * Retrieves an agent by their ID.
     *
     * @param id the ID of the agent
     * @return an Optional containing the agent if found, or empty if not found
     */
    public Optional<Agent> findById(Long id) {
        return agentRepository.findById(id);
    }

    /**
     * Retrieves a list of all agents in the database.
     *
     * @return a list of all agents
     */
    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }
    
    // Check if the agent has any associated listings
    public boolean hasListings(Long agentId) {
        // Query the ListingRepository to check if any listings exist with the given agentId
        return listingRepository.existsByAgentId(agentId);
    }

    /**
     * Updates the details of an existing agent.
     *
     * @param agent the agent object with updated details
     * @return the updated agent
     * @throws AgentNotFoundException if no agent with the specified ID is found
     */
    @Transactional
    public Agent updateAgent(Agent agent) {
        Agent existingAgent = agentRepository.findById(agent.getId())
                .orElseThrow(() -> new AgentNotFoundException("Agent with ID " + agent.getId() + " not found!"));

        // Update fields
        existingAgent.setFirstName(agent.getFirstName());
        existingAgent.setLastName(agent.getLastName());
        existingAgent.setEmail(agent.getEmail());
        existingAgent.setPhone(agent.getPhone());
        existingAgent.setBio(agent.getBio());
        existingAgent.setLicenseNumber(agent.getLicenseNumber());

        return agentRepository.save(existingAgent);
    }

    /**
     * Deletes an agent by their ID.
     *
     * @param id the ID of the agent to delete
     * @throws AgentNotFoundException if no agent with the specified ID is found
     */
    @Transactional
    public void deleteAgent(Long id) {
        Agent existingAgent = agentRepository.findById(id)
                .orElseThrow(() -> new AgentNotFoundException("Agent with ID " + id + " not found!"));

        agentRepository.delete(existingAgent);
    }
}
