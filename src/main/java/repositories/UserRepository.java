package com.realestate.app.repositories;

import com.realestate.app.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by username.
     * 
     * @param username the username of the user
     * @return an Optional containing the User if found, or empty if not
     */
    Optional<User> findByUsername(String username);


    /**
     * Finds all users containing a specific role.
     * 
     * @param role the role to filter by
     * @return a list of users with the specified role
     */
    Page<User> findAll(Pageable pageable);

}
