package com.realestate.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.app.models.Agent;
import com.realestate.app.repositories.AgentRepository;

import jakarta.transaction.Transactional;

@Service
public class AgentService {
    @Autowired
    private AgentRepository agentRepository;

    Agent agent = new Agent();
    
    @Transactional
    public Agent createAgent(Agent agent) {
        return agentRepository.save(agent);
    }

    public Agent findAgentByEmail(String email) {
        return agentRepository.findByEmail(email);
    }
    
    public Optional<Agent> findById(Long id) {
    	return agentRepository.findById(id);
    }
    
    public List<Agent> getAllAgents() {
    	return agentRepository.findAll();
    }
    
    @Transactional
    public Agent updateAgent(Agent agent) {
        Optional<Agent> existingAgent = agentRepository.findById(agent.getId());
        if (existingAgent.isPresent()) {
            // Update existing agent details (excluding ID)
            existingAgent.get().setFirstName(agent.getFirstName());
            existingAgent.get().setLastName(agent.getLastName());
            existingAgent.get().setEmail(agent.getEmail());
            existingAgent.get().setPhone(agent.getPhone());
            existingAgent.get().setBio(agent.getBio());
            existingAgent.get().setLicenseNumber(agent.getLicenseNumber());
            existingAgent.get().setCreatedAt(agent.getCreatedAt());
            
            return agentRepository.save(existingAgent.get());
        } else {           
            throw new RuntimeException("Agent with ID " + agent.getId() + " not found!");
        }
    }

    @Transactional
    public void deleteAgent(Long id) {
        
        Optional<Agent> existingAgent = agentRepository.findById(id);
        if (existingAgent.isPresent()) {
            agentRepository.delete(existingAgent.get());
        } else {
        	throw new RuntimeException("Agent with ID " + agent.getId() + " not found!");
        }
    }
    
}