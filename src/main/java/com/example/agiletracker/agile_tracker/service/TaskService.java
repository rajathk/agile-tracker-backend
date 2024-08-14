package com.example.agiletracker.agile_tracker.service;

import com.example.agiletracker.agile_tracker.constants.ProjectStatus;
import com.example.agiletracker.agile_tracker.constants.TaskStatus;
import com.example.agiletracker.agile_tracker.entity.Audit;
import com.example.agiletracker.agile_tracker.entity.Task;
import com.example.agiletracker.agile_tracker.repository.AuditRepository;
import com.example.agiletracker.agile_tracker.repository.TaskRepository;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class TaskService {

    @Autowired
    private AuditService auditService;

    @Autowired
    private TaskRepository taskRepository;

    public Task getTask(int id){
        log.info("task service: get task request received for id = {}", id);
        return taskRepository.getTask(id);
    }

    public List<Task> getAllTasks(int projectId){
        log.info("task service: get all tasks request received");
        return taskRepository.getAllTasks(projectId);
    }

    public Task createTask(Task task){
        log.info("task service: create task request received for task = {}", task);
        TaskStatus taskStatus = switch (task.getStatus()) {
            case "CREATED" -> TaskStatus.CREATED;
            case "IN_PROGRESS" -> TaskStatus.IN_PROGRESS;
            case "COMPLETED" -> TaskStatus.COMPLETED;
            default -> TaskStatus.CANCELLED;
        };
        task.setStatus(taskStatus.toString());
        task = taskRepository.create(task);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("event", "new task created");
        return getTask(task, jsonObject);
    }

    public Task updateTask(int id, Task task){
        log.info("task service: update task request received for id = {} and task = {}", id, task);
        TaskStatus taskStatus = switch (task.getStatus()) {
            case "CREATED" -> TaskStatus.CREATED;
            case "IN_PROGRESS" -> TaskStatus.IN_PROGRESS;
            case "COMPLETED" -> TaskStatus.COMPLETED;
            default -> TaskStatus.CANCELLED;
        };
        task.setStatus(taskStatus.toString());
        task = taskRepository.update(id, task);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("event", "task updated");
        return getTask(task, jsonObject);
    }

    private Task getTask(Task task1, JsonObject jsonObject) {
        jsonObject.addProperty("project_id", task1.getProj_id());
        Audit audit = new Audit();
        audit.setUser_id(task1.getCreated_by());
        audit.setCreated_on(task1.getCreated_on());
        audit.setEvent(jsonObject);
        auditService.createAudit(audit);
        return task1;
    }

    public String deleteTask(int id){
        log.info("task service: delete task request received for id = {}", id);
        return taskRepository.delete(id);
    }
}
