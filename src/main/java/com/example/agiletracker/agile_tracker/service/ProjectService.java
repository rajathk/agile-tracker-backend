package com.example.agiletracker.agile_tracker.service;
import com.example.agiletracker.agile_tracker.constants.ProjectStatus;
import com.example.agiletracker.agile_tracker.entity.Audit;
import com.example.agiletracker.agile_tracker.entity.Project;
import com.example.agiletracker.agile_tracker.repository.ProjectRepository;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ProjectService {

    @Autowired
    private AuditService auditService;

    @Autowired
    private ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    public Project getProject(int id) {
        log.info("project service: get project request received for id = {}", id);
        return projectRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Project> getAllProjects(int userId) {
        log.info("project service: get all projects request received");
        return projectRepository.findByCreatedBy(userId);
    }

    public List<Project> getProjectsByStatus(ProjectStatus status) {
        log.info("project service: get projects by status request received");
        return projectRepository.findByStatus(status);
    }

    public List<Project> getProjectsByDateAndStatus(LocalDateTime startDate, LocalDateTime endDate, ProjectStatus status) {
        log.info("project service: get projects by date and status request received");
        return projectRepository.findProjectByDateBetweenAndStatus(startDate, endDate, status.toString());
    }


    @Transactional
    public Project createProject(Project project) {
        log.info("project service: create project request received for project = {}", project);
        project.setCreatedOn(LocalDateTime.now());
        project.setModifiedOn(LocalDateTime.now());
        project = projectRepository.save(project);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("event", "new project created");
        jsonObject.addProperty("project_id", project.getId());

        //Audit audit = new Audit();
        //audit.setUserId(project.getCreatedBy());
        //audit.setCreatedOn(project.getCreatedOn());
        //audit.setEvent(jsonObject);
        //auditService.createAudit(audit);

        return project;
    }

    @Transactional
    public Project updateProject(int id, Project project) {
        log.info("project service: update project request received for id = {} and project = {}", id, project);
        Project existingProject = projectRepository.findById(id).orElse(null);
        if (existingProject == null) {
            return null;
        }

        existingProject.setName(project.getName());
        existingProject.setDescription(project.getDescription());
        existingProject.setStartDate(project.getStartDate());
        existingProject.setEndDate(project.getEndDate());
        existingProject.setStatus(project.getStatus());
        existingProject.setModifiedBy(project.getModifiedBy());
        existingProject.setModifiedOn(LocalDateTime.now());

        existingProject = projectRepository.save(existingProject);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("event", "project updated");
        jsonObject.addProperty("project_id", existingProject.getId());

//        Audit audit = new Audit();
//        audit.setUserId(existingProject.getModifiedBy());
//        audit.setCreatedOn(existingProject.getModifiedOn());
//        audit.setEvent(jsonObject);
//        auditService.createAudit(audit);

        return existingProject;
    }

    @Transactional
    public String deleteProject(int id) {
        log.info("project service: delete project request received for id = {}", id);
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return "Project deleted successfully";
        } else {
            return "Project not found";
        }
    }
}

//@Slf4j
//@Service
//public class ProjectService {
//
//    @Autowired
//    private AuditService auditService;
//
//    @Autowired
//    private ProjectRepository projectRepository;
//
//    public Project getProject(int id){
//        log.info("project service: get project request received for id = {}", id);
//        return projectRepository.getReferenceById(id);
//    }
//
//    public List<Project> getAllProjects(int user_id){
//        log.info("project service: get all projects request received");
//        return projectRepository.getAllProjects(user_id);
//    }
//
//    public Project createProject(Project project){
//        log.info("project service: create project request received for project = {}", project);
//        ProjectStatus projectStatus = switch (project.getStatus()) {
//            case "CREATED" -> ProjectStatus.CREATED;
//            case "IN_PROGRESS" -> ProjectStatus.IN_PROGRESS;
//            default -> ProjectStatus.COMPLETED;
//        };
//        project.setStatus(projectStatus.toString());
//        project = projectRepository.create(project);
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("event", "new project created");
//        jsonObject.addProperty("project_id", project.getId());
//        Audit audit = new Audit();
//        audit.setUser_id(project.getCreated_by());
//        audit.setCreated_on(project.getCreated_on());
//        audit.setEvent(jsonObject);
//        auditService.createAudit(audit);
//        return project;
//    }
//
//    public Project updateProject(int id, Project project){
//        log.info("project service: update project request received for id = {} and project = {}", id, project);
//        ProjectStatus projectStatus = switch (project.getStatus()) {
//            case "CREATED" -> ProjectStatus.CREATED;
//            case "IN_PROGRESS" -> ProjectStatus.IN_PROGRESS;
//            default -> ProjectStatus.COMPLETED;
//        };
//        project.setStatus(projectStatus.toString());
//        project = projectRepository.update(id, project);
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("event", "project updated");
//        jsonObject.addProperty("project_id", project.getId());
//        Audit audit = new Audit();
//        audit.setUser_id(project.getModified_by());
//        audit.setCreated_on(project.getModified_on());
//        audit.setEvent(jsonObject);
//        auditService.createAudit(audit);
//        return project;
//    }
//
//    public String deleteProject(int id){
//        log.info("project service: delete project request received for id = {}", id);
//        return projectRepository.delete(id);
//    }
//}
