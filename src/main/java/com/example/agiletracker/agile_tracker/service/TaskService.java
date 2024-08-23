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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class TaskService {

    @Autowired
    private AuditService auditService;

    @Autowired
    private TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public Task getTask(int id){
        log.info("task service: get task request received for id = {}", id);
        return taskRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Task> getAllTasks(int projectId){
        log.info("task service: get all tasks request received");
        return taskRepository.findByProjectId(projectId);
    }

    @Transactional(readOnly = true)
    public List<Task> getTasksByProjectAndStatus(int projectId,TaskStatus status){
        log.info("task service: get tasks by status request received");
        return taskRepository.findByProjectIdAndStatus(projectId, status);
    }

    @Transactional
    public Task createTask(Task task){
        log.info("task service: create task request received for task = {}", task);
        task.setStatus(TaskStatus.CREATED);
        task = taskRepository.save(task);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("event", "new task created");
        return getTask(task, jsonObject);
    }

    @Transactional
    public Task updateTask(int id, Task task){
        log.info("task service: update task request received for id = {} and task = {}", id, task);
        Task existingTask = taskRepository.findById(id).orElse(null);
        if (existingTask == null) {
            return null;
        }
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setAssignee(task.getAssignee());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setModifiedBy(task.getModifiedBy());
        existingTask.setModifiedOn(task.getModifiedOn());

        task.setStatus(task.getStatus());
        existingTask = taskRepository.save(existingTask);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("event", "task updated");
        return getTask(existingTask, jsonObject);
    }
    @Transactional
    protected Task getTask(Task task, JsonObject jsonObject) {
        jsonObject.addProperty("project_id", task.getProject().getId());
        Audit audit = new Audit();
        audit.setUser_id(task.getCreatedBy());
        audit.setCreated_on(task.getCreatedOn());
        audit.setEvent(jsonObject);
        auditService.createAudit(audit);
        return task;
    }

    @Transactional
    public String deleteTask(int id){
        log.info("task service: delete task request received for id = {}", id);
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return "Task deleted successfully";
        } else {
            return "Task not found";
        }
    }
}
