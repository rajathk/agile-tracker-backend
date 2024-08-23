package com.example.agiletracker.agile_tracker.controller;

import com.example.agiletracker.agile_tracker.constants.TaskStatus;
import com.example.agiletracker.agile_tracker.entity.Task;
//import com.example.agiletracker.agile_tracker.service.TaskService;
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
        Task task = taskService.getTask(id);
        return task != null ? new ResponseEntity<>(task, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/tasks/project/{projectId}")
    public ResponseEntity<List<Task>> getAllTasks(@PathVariable int projectId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("task controller: get all tasks request received for user: {}", auth.getName());
        return new ResponseEntity<>(taskService.getAllTasks(projectId), HttpStatus.OK);
    }

    @GetMapping("/tasks/project/{projectId}/status/{status}")
    public ResponseEntity<List<Task>> getTasksByProjectAndStatus( @PathVariable int projectId, @PathVariable TaskStatus status) {
        log.info("task controller: get tasks by status request received");
        return new ResponseEntity<>(taskService.getTasksByProjectAndStatus(projectId, status), HttpStatus.OK);
    }

    @PostMapping("/task/create")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        log.info("task controller: create task request received for task = {}", task);
        return new ResponseEntity<>(taskService.createTask(task), HttpStatus.CREATED);
    }

    @PutMapping("/task/update/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable int taskId, @RequestBody Task task) {
        log.info("task controller: update task request received for id = {} and task = {}", taskId, task);
        Task existingTask = taskService.getTask(taskId);
        if (existingTask == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(taskService.updateTask(taskId, task), HttpStatus.OK);
    }

    @DeleteMapping("/task/delete/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable int taskId) {
        log.info("task controller: delete task request received for id = {}", taskId);
        Task existingTask = taskService.getTask(taskId);
        if (existingTask == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(taskService.deleteTask(taskId), HttpStatus.OK);
    }
}
