package com.example.agiletracker.agile_tracker.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {

    private int id;
    private String username;
    private String password;
    private String email;

}

