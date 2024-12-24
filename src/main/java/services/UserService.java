package com.realestate.app.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.app.models.User;
import com.realestate.app.repositories.UserRepository;

/**
 * Service class that provides operations for managing {@link User} entities.
 * It interacts with the {@link UserRepository} to perform actions on users.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to be found
     * @return an {@link Optional} containing the user if found, empty if not
     */
    public Optional<User> findByUsername(String username) {
        System.out.println("Looking for user: " + username);
        return userRepository.findByUsername(username);
    }

    /**
     * Saves a user entity to the repository.
     *
     * @param user the user entity to be saved
     */
    public void save(User user) {
        userRepository.save(user);
    }
}
