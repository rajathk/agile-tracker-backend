package com.example.agiletracker.agile_tracker.service;
import com.example.agiletracker.agile_tracker.constants.ProjectStatus;
import com.example.agiletracker.agile_tracker.entity.Audit;
import com.example.agiletracker.agile_tracker.entity.Project;
import com.example.agiletracker.agile_tracker.repository.ProjectRepository;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class ProjectService {

    @Autowired
    private AuditService auditService;

    @Autowired
    private ProjectRepository projectRepository;

    public Project getProject(int id){
        log.info("project service: get project request received for id = {}", id);
        return projectRepository.getProject(id);
    }

    public List<Project> getAllProjects(int user_id){
        log.info("project service: get all projects request received");
        return projectRepository.getAllProjects(user_id);
    }

    public Project createProject(Project project){
        log.info("project service: create project request received for project = {}", project);
        ProjectStatus projectStatus = switch (project.getStatus()) {
            case "CREATED" -> ProjectStatus.CREATED;
            case "IN_PROGRESS" -> ProjectStatus.IN_PROGRESS;
            default -> ProjectStatus.COMPLETED;
        };
        project.setStatus(projectStatus.toString());
        project = projectRepository.create(project);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("event", "new project created");
        jsonObject.addProperty("project_id", project.getId());
        Audit audit = new Audit();
        audit.setUser_id(project.getCreated_by());
        audit.setCreated_on(project.getCreated_on());
        audit.setEvent(jsonObject);
        auditService.createAudit(audit);
        return project;
    }

    public Project updateProject(int id, Project project){
        log.info("project service: update project request received for id = {} and project = {}", id, project);
        ProjectStatus projectStatus = switch (project.getStatus()) {
            case "CREATED" -> ProjectStatus.CREATED;
            case "IN_PROGRESS" -> ProjectStatus.IN_PROGRESS;
            default -> ProjectStatus.COMPLETED;
        };
        project.setStatus(projectStatus.toString());
        project = projectRepository.update(id, project);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("event", "project updated");
        jsonObject.addProperty("project_id", project.getId());
        Audit audit = new Audit();
        audit.setUser_id(project.getModified_by());
        audit.setCreated_on(project.getModified_on());
        audit.setEvent(jsonObject);
        auditService.createAudit(audit);
        return project;
    }

    public String deleteProject(int id){
        log.info("project service: delete project request received for id = {}", id);
        return projectRepository.delete(id);
    }
}
