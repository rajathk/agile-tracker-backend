package com.example.agiletracker.agile_tracker.controller;

import com.example.agiletracker.agile_tracker.entity.Task;
import com.example.agiletracker.agile_tracker.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/task")
    public ResponseEntity<Task> getTask(@RequestParam int id) {
        log.info("task controller: get task request received for id = {}", id);
        return new ResponseEntity<>(taskService.getTask(id), HttpStatus.OK);
    }

    @GetMapping("/tasks/project/{projectId}")
    public ResponseEntity<List<Task>> getAllTasks(@PathVariable int projectId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("task controller: get all tasks request received for user: {}", auth.getName());
        return new ResponseEntity<>(taskService.getAllTasks(projectId), HttpStatus.OK);
    }

    @PostMapping("/task/create")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        log.info("task controller: create task request received for task = {}", task);
        return new ResponseEntity<>(taskService.createTask(task), HttpStatus.CREATED);
    }

    @PutMapping("/task/update/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable int taskId, @RequestBody Task task) {
        log.info("task controller: update task request received for id = {} and task = {}", taskId, task);
        return new ResponseEntity<>(taskService.updateTask(taskId, task), HttpStatus.OK);
    }

    @DeleteMapping("/task/delete/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable int taskId) {
        log.info("task controller: delete task request received for id = {}", taskId);
        return new ResponseEntity<>(taskService.deleteTask(taskId), HttpStatus.OK);
    }
}
