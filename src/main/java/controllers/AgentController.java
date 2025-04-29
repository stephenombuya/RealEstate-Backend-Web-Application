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
import com.realestate.app.models.Agent;
import com.realestate.app.services.AgentService;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
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
     */
    @PostMapping
    @RateLimiter(name = "writeOperations", fallbackMethod = "rateLimiterFallback")
    @Operation(summary = "Registers a new agent", 
        description = "Registers a new agent with provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Agent registered successfully"),
        @ApiResponse(responseCode = "400", description = "Username already taken")
    })
    public ResponseEntity<Agent> createAgent(@Validated @RequestBody Agent agent) {
        try {
            Agent createdAgent = agentService.createAgent(agent);
            
            // Send email notification logic here (commented out in original)
            
            return new ResponseEntity<>(createdAgent, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves all agents.
     */
    @GetMapping
    @RateLimiter(name = "searchOperations", fallbackMethod = "rateLimiterFallback")
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
     */
    @GetMapping("/email/{email}")
    @RateLimiter(name = "standardApi", fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<Agent> getAgentByEmail(@PathVariable String email) {
        Optional<Agent> agentOptional = Optional.ofNullable(agentService.findAgentByEmail(email));
        return agentOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Retrieves an agent by their ID.
     */
    @GetMapping("/{id}")
    @RateLimiter(name = "standardApi", fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<Agent> getAgent(@PathVariable Long id) {
        return agentService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Updates an existing agent's details.
     */
    @PutMapping("/{id}")
    @RateLimiter(name = "writeOperations", fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<Agent> updateAgent(@PathVariable("id") Long id, @Validated @RequestBody Agent agent) {
        try {
            agent.setId(id);
            Agent updatedAgent = agentService.updateAgent(agent);
            return new ResponseEntity<>(updatedAgent, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes an agent by their ID.
     */
    @DeleteMapping("/{id}")
    @RateLimiter(name = "criticalOperations", fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<Object> deleteAgent(@PathVariable Long id) {
        try {
            if (agentService.hasListings(id)) {
                String errorMessage = "Cannot delete agent with ID " + id + " because they have associated listings.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse("BAD_REQUEST", errorMessage));
            }
            
            agentService.deleteAgent(id);
            return ResponseEntity.noContent().build();
        } catch (AgentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Fallback method for rate-limited endpoints
     */
    public ResponseEntity<Object> rateLimiterFallback(Exception ex) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body("Rate limit exceeded. Please try again later.");
    }
}
