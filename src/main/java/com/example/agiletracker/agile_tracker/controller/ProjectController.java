package com.example.agiletracker.agile_tracker.controller;
import com.example.agiletracker.agile_tracker.constants.ProjectStatus;
import com.example.agiletracker.agile_tracker.entity.Project;
import com.example.agiletracker.agile_tracker.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/project/{id}")
    public ResponseEntity<Project> getProject(@PathVariable int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("project controller: get project request received for id = {} by user: {}", id, auth.getName());
        Project project = projectService.getProject(id);
        return project != null ? new ResponseEntity<>(project, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/projects/user/{userId}")
    public ResponseEntity<List<Project>> getAllProjects(@PathVariable int userId) {
        log.info("project controller: get all projects request received for user: {}", userId);
        return new ResponseEntity<>(projectService.getAllProjects(userId), HttpStatus.OK);
    }

    @GetMapping("/projects/status/{status}")
    public ResponseEntity<List<Project>> getProjectsByStatus(@PathVariable ProjectStatus status) {
        log.info("project controller: get projects by status request received");
        return new ResponseEntity<>(projectService.getProjectsByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/projects/date/{startDate}/{endDate}/status/{status}")
    public ResponseEntity<List<Project>> getProjectsByDateAndStatus(@PathVariable LocalDateTime startDate, @PathVariable LocalDateTime endDate, @PathVariable ProjectStatus status) {
        log.info("project controller: get projects by date request received");
        return new ResponseEntity<>(projectService.getProjectsByDateAndStatus(startDate, endDate, status), HttpStatus.OK);
    }

    @PostMapping("/project/create")
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("project controller: create project request received for project = {} by user: {}", project, auth.getName());
        return new ResponseEntity<>(projectService.createProject(project), HttpStatus.CREATED);
    }

    @PutMapping("/project/update/{projectId}")
    public ResponseEntity<Project> updateProject(@PathVariable int projectId, @RequestBody Project project) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("project controller: update project request received for id = {} and project = {} by user: {}", projectId, project, auth.getName());
        Project updatedProject = projectService.updateProject(projectId, project);
        return updatedProject != null ? new ResponseEntity<>(updatedProject, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/project/delete/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable int projectId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("project controller: delete project request received for id = {} by user: {}", projectId, auth.getName());
        String result = projectService.deleteProject(projectId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

//@Slf4j
//@RestController
//@RequestMapping("/api")
//public class ProjectController {
//
//    @Autowired
//    private ProjectService projectService;
//
//    @GetMapping("/project/{id}")
//    public ResponseEntity<Project> getProject(@PathVariable int id) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth.isAuthenticated()){
//            log.info("project controller: get project request received for id = {} by user: {}", id, auth.getName());
//            return new ResponseEntity<>(projectService.getProject(id), HttpStatus.OK);
//        }else{
//            log.info("project controller: get project request received for id = {} by anonymous user", id);
//            return new ResponseEntity<>(projectService.getProject(id), HttpStatus.OK);
//        }
//    }
//
//    @GetMapping("/projects/user/{userId}")
//    public ResponseEntity<List<Project>> getAllProjects(@PathVariable int userId) {
//        log.info("project controller: get all projects request received for user: {}", userId);
//        return new ResponseEntity<>(projectService.getAllProjects(userId), HttpStatus.OK);
//    }
//
//    @PostMapping("/project/create")
//    public ResponseEntity<Project> createProject(@RequestBody Project project) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        log.info("project controller: create project request received for project = {} by user: {}", project, auth.getName());
//        return new ResponseEntity<>(projectService.createProject(project), HttpStatus.CREATED);
//    }
//
//    @PutMapping("/project/update/{projectId}")
//    public ResponseEntity<Project> updateProject(@PathVariable int projectId, @RequestBody Project project) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        log.info("project controller: update project request received for id = {} and project = {} by user: {}", projectId, project, auth.getName());
//        return new ResponseEntity<>(projectService.updateProject(projectId, project), HttpStatus.OK);
//    }
//
//    @DeleteMapping("/project/delete/{projectId}")
//    public ResponseEntity<String> deleteProject(@PathVariable int projectId) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        log.info("project controller: delete project request received for id = {} by user: {}", projectId, auth.getName());
//        return new ResponseEntity<>(projectService.deleteProject(projectId), HttpStatus.OK);
//    }
//}
