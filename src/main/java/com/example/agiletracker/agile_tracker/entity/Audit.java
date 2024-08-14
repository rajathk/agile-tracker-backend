package com.example.agiletracker.agile_tracker.entity;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Audit {
    private int user_id;
    private LocalDateTime created_on;
    private JsonObject event;
}
