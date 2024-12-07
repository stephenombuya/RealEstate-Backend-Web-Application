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

import com.realestate.app.models.Agent;
import com.realestate.app.services.AgentService;

@RestController
@RequestMapping("/api/agents")
public class AgentController {
    @Autowired
    private AgentService agentService;

    @PostMapping
    public ResponseEntity<Agent> createAgent(@Validated @RequestBody Agent agent) {
        return new ResponseEntity<>(agentService.createAgent(agent), HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<Agent>> getAllAgents() {
    	return ResponseEntity.ok(agentService.getAllAgents());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Agent> getAgentByEmail(@PathVariable String email) {
        Optional<Agent> agentOptional = Optional.ofNullable(agentService.findAgentByEmail(email));
        return agentOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Agent> getAgent(@PathVariable Long id) {
        return agentService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Agent> updateAgent(@Validated @RequestBody Agent agent) {
       return new ResponseEntity<>(agentService.updateAgent(agent), HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Agent> deleteAgent(@PathVariable Long id) {
    	agentService.deleteAgent(id);
    	return ResponseEntity.noContent().build();
    }
}
