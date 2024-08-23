package com.example.agiletracker.agile_tracker.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_by")
    private int createdBy;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "modified_by")
    private int modifiedBy;

    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

    @ManyToOne
    @JoinColumn(name = "task_id")
    @JsonBackReference
    private Task task;
}
