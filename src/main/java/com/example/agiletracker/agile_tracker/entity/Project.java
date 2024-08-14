package com.example.agiletracker.agile_tracker.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Project {
    private int id;
    private String name;
    private String description;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String status;
    private int created_by;
    private LocalDateTime created_on;
    private int modified_by;
    private LocalDateTime modified_on;

    public Project(int projectId, String name, String description,
                   LocalDateTime start_date, LocalDateTime end_date, String status, int created_by,
                   LocalDateTime created_on, int modified_by, LocalDateTime modified_on) {
        this.id = projectId;
        this.name = name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.created_by = created_by;
        this.created_on = created_on;
        this.modified_by = modified_by;
        this.modified_on = modified_on;
    }
}
