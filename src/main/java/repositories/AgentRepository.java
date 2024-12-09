package com.realestate.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realestate.app.models.Agent;

public interface AgentRepository extends JpaRepository<Agent, Long> {

	 Agent findByEmail(String email);
	 List<Agent> findByLastName(String lastName);
}
