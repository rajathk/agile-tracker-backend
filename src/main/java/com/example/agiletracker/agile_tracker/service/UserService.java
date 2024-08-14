package com.example.agiletracker.agile_tracker.service;

import com.example.agiletracker.agile_tracker.entity.User;
import com.example.agiletracker.agile_tracker.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User userLogin(String username, String password) {
        log.info("user service: login request received for username = {}", username);
        return userRepository.getLogin(username, password);
    }

    public User getUserDetails(String username) {
        return userRepository.getUserDetails(username);
    }
}
