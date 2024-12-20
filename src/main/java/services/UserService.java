package com.realestate.app.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.app.models.User;
import com.realestate.app.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
    	System.out.println("Looking for user: " + username);
    	return userRepository.findByUsername(username);
    }

    public void save(User user) { 
        userRepository.save(user);
    }
}
