package com.example.agiletracker.agile_tracker.controller;

import com.example.agiletracker.agile_tracker.entity.Comment;
import com.example.agiletracker.agile_tracker.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/comments/task/{taskId}")
    public List<Comment> getCommentsByTaskId(@PathVariable int taskId) {
        return commentService.getCommentsByTaskId(taskId);
    }

    @PostMapping("/comment/create")
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.createComment(comment);
    }

    @PutMapping("/comment/update/{commentId}")
    public Comment updateComment(@PathVariable int commentId, @RequestBody Comment comment) {
        Comment existingComment = commentService.getComment(commentId);
        if (existingComment == null) {
            return null;
        }
        return commentService.updateComment(commentId, comment);
    }

    @DeleteMapping("/comment/delete/{commentId}")
    public String deleteComment(@PathVariable int commentId) {
        return commentService.deleteComment(commentId);
    }

}
