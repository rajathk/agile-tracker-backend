package com.example.agiletracker.agile_tracker.repository;

import com.example.agiletracker.agile_tracker.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findCommentsByTaskId(int taskId);
}
