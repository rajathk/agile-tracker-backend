package com.example.agiletracker.agile_tracker.entity;
import com.example.agiletracker.agile_tracker.constants.TaskStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "proj_id")
    @JsonBackReference
    private Project project;

    private String title;
    private String description;
    private int assignee;
    private String priority;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "created_by")
    private int createdBy;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "modified_by")
    private int modifiedBy;

    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Comment> comments;
}
