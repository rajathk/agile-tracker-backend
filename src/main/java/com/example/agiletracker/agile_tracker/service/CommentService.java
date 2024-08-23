package com.example.agiletracker.agile_tracker.service;

import com.example.agiletracker.agile_tracker.entity.Comment;
import com.example.agiletracker.agile_tracker.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment getComment(int id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsByTaskId(int taskId) {
        return commentRepository.findCommentsByTaskId(taskId);
    }

    @Transactional
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(int id, Comment comment) {
        Comment existingComment = commentRepository.findById(id).orElse(null);
        if (existingComment == null) {
            return null;
        }
        existingComment.setComment(comment.getComment());
        existingComment.setModifiedBy(comment.getModifiedBy());
        existingComment.setModifiedOn(comment.getModifiedOn());
        return commentRepository.save(comment);
    }

    @Transactional
    public String deleteComment(int id) {
        if (commentRepository.findById(id).orElse(null) == null) {
            return "comment not found";
        }
        commentRepository.deleteById(id);
        return "comment deleted successfully";
    }
}
