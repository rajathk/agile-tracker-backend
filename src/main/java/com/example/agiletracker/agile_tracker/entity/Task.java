package com.example.agiletracker.agile_tracker.entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Task {

    private int id;
    private int proj_id;
    private String title;
    private String description;
    private int assignee;
    private String priority;
    private String status;
    private LocalDateTime due_date;
    private int created_by;
    private LocalDateTime created_on;
    private int modified_by;
    private LocalDateTime modified_on;


    public Task(int taskId, int projId, String title, String description, int assignee, String priority, String status,
                LocalDateTime due_date, int created_by, LocalDateTime created_on, int modified_by, LocalDateTime modified_on) {
        this.id = taskId;
        this.proj_id = projId;
        this.title = title;
        this.description = description;
        this.assignee = assignee;
        this.priority = priority;
        this.status = status;
        this.due_date = due_date;
        this.created_by = created_by;
        this.created_on = created_on;
        this.modified_by = modified_by;
        this.modified_on = modified_on;
    }

}
