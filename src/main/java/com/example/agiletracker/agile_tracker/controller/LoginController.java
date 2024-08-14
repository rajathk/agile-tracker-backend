package com.example.agiletracker.agile_tracker.controller;

import com.example.agiletracker.agile_tracker.entity.User;
import com.example.agiletracker.agile_tracker.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// THIS IS NO LONGER USED, CREATED FOR TESTING
@Slf4j
@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<User> getLogin(@RequestParam("username") String username, @RequestParam("password") String password){
        log.info("login controller: login request received for username = {}", username);
        if (!username.isEmpty() && !password.isEmpty()){
            User user = userService.userLogin(username, password);
            if (user != null) {
                log.info("login controller: login successful for username = {}", user.getUsername());
                return ResponseEntity.ok(user);
            } else {
                log.info("login controller: login failed due to invalid username or password");
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
