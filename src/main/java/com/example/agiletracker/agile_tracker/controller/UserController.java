package com.example.agiletracker.agile_tracker.controller;

import com.example.agiletracker.agile_tracker.entity.User;
import com.example.agiletracker.agile_tracker.repository.RedisJwtTokenRepository;
import com.example.agiletracker.agile_tracker.service.UserService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisJwtTokenRepository redisJwtTokenRepository;

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserDetails(@PathVariable String username) {
        return new ResponseEntity<>(userService.getUserDetails(username), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String token) {
        System.out.println("Logging out user with token: " + token);
        String jwtToken = token.substring(7);
        redisJwtTokenRepository.addToBlacklist(jwtToken);
    }

}
