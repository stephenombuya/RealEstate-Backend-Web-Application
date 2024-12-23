package com.realestate.app.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.realestate.app.exceptionHandlers.AgentNotFoundException;
import com.realestate.app.exceptionHandlers.ErrorResponse;
import com.realestate.app.services.AgentService;
import com.realestate.app.models.Agent;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Controller class for handling agent-related operations.
 */
@RestController
@RequestMapping("/api/agents")
public class AgentController {

    @Autowired
    private AgentService agentService;

    /**
     * Registers a new agent with their details.
     * 
     * @param agent The agent details to be registered.
     * @return A ResponseEntity containing the registered agent.
     */
    @PostMapping
    @Operation(summary = "Involved in user registration", 
        description = "Users are registered using their unique username, password, and roles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Agent registered successfully"),
        @ApiResponse(responseCode = "400", description = "Username already taken")
    })
    public ResponseEntity<Agent> createAgent(@Validated @RequestBody Agent agent) {
        try {
            // Create a new agent instead of updating
            Agent createdAgent = agentService.createAgent(agent);
            return new ResponseEntity<>(createdAgent, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle specific exception for duplicate username
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves all agents.
     * 
     * @return A ResponseEntity containing a list of all agents.
     */
    @GetMapping
    public ResponseEntity<List<Agent>> getAllAgents() {
        try {
            List<Agent> agents = agentService.getAllAgents();
            return ResponseEntity.ok(agents);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves an agent by their email address.
     * 
     * @param email The email of the agent to be retrieved.
     * @return A ResponseEntity containing the agent or a 404 Not Found if not found.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Agent> getAgentByEmail(@PathVariable String email) {
        Optional<Agent> agentOptional = Optional.ofNullable(agentService.findAgentByEmail(email));
        return agentOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Retrieves an agent by their ID.
     * 
     * @param id The ID of the agent to be retrieved.
     * @return A ResponseEntity containing the agent or a 404 Not Found if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Agent> getAgent(@PathVariable Long id) {
        return agentService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Updates an existing agent's details.
     * 
     * @param id The ID of the agent to be updated.
     * @param agent The updated agent details.
     * @return A ResponseEntity containing the updated agent.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Agent> updateAgent(@PathVariable("id") Long id, @Validated @RequestBody Agent agent) {
        try {
            agent.setId(id); // Set the ID from the URL to the request body
            Agent updatedAgent = agentService.updateAgent(agent);
            return new ResponseEntity<>(updatedAgent, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 if the agent is not found
        }
    }

    /**
     * Deletes an agent by their ID.
     * 
     * @param id The ID of the agent to be deleted.
     * @return A ResponseEntity indicating the deletion status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAgent(@PathVariable Long id) {
        try {
            // Check if the agent has any associated listings
            if (agentService.hasListings(id)) {
                // Return a BAD_REQUEST (400) with a custom error message
                String errorMessage = "Cannot delete agent with ID " + id + " because they have associated listings.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse("BAD_REQUEST", errorMessage)); // Return custom error response
            }
            
            // Proceed with agent deletion if no listings are associated
            agentService.deleteAgent(id);
            return ResponseEntity.noContent().build(); // No content for successful deletion
        } catch (AgentNotFoundException e) {
            // Return 404 if the agent is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Return a general error if any other exception occurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
