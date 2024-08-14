package com.example.agiletracker.agile_tracker.repository;

import com.example.agiletracker.agile_tracker.entity.Project;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class ProjectRepository {

    @Autowired
    private DataSource dataSource;

    @Value("${project.read.query}")
    private String getProjectQuery;

    @Value("${project.read.all.query}")
    private String getAllProjectsQuery;

    @Value("${project.create.query}")
    private String createProjectQuery;

    @Value("${project.update.query}")
    private String updateProjectQuery;

    @Value("${project.delete.query}")
    private String deleteProjectQuery;

    public Project getProject(int id){
        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getProjectQuery)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                log.info("project repository: found project for id = {}", id);
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setStart_date(resultSet.getTimestamp("start_date").toLocalDateTime());
                project.setEnd_date(resultSet.getTimestamp("end_date").toLocalDateTime());
                project.setStatus(resultSet.getString("status"));
                project.setCreated_by(resultSet.getInt("created_by"));
                project.setCreated_on(resultSet.getTimestamp("created_on").toLocalDateTime());
                project.setModified_by(resultSet.getInt("modified_by"));
                project.setModified_on(resultSet.getTimestamp("modified_on").toLocalDateTime());
                return project;
            } else {
                log.info("project repository: no project found for id = {}", id);
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Project> getAllProjects(int user_id){
        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getAllProjectsQuery)){
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Project> projects = new ArrayList<>();
            log.info("project repository: get all projects request received");
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setStart_date(resultSet.getTimestamp("start_date").toLocalDateTime());
                project.setEnd_date(resultSet.getTimestamp("end_date").toLocalDateTime());
                project.setStatus(resultSet.getString("status"));
                project.setCreated_by(resultSet.getInt("created_by"));
                project.setCreated_on(resultSet.getTimestamp("created_on").toLocalDateTime());
                project.setModified_by(resultSet.getInt("modified_by"));
                project.setModified_on(resultSet.getTimestamp("modified_on").toLocalDateTime());
                projects.add(project);
            }
            return projects;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Project create(Project project){
        log.info("project repository: create project request received for project = {}", project);
        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(createProjectQuery)){
            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getDescription());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(project.getStart_date()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(project.getEnd_date()));
            preparedStatement.setString(5, project.getStatus());
            preparedStatement.setInt(6, project.getCreated_by());
            preparedStatement.setInt(7, project.getModified_by());
            return getProject(project, preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Project update(int id, Project project){
        log.info("project repository: update project request received for id = {} and project = {}", id, project);
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(updateProjectQuery)) {
            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getDescription());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(project.getStart_date()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(project.getEnd_date()));
            preparedStatement.setString(5, project.getStatus());
            preparedStatement.setInt(6, project.getModified_by());
            preparedStatement.setLong(7, id);
            return getProject(project, preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating project", e);
        }
    }

    public String delete(int id){
        log.info("project repository: delete project request received for id = {}", id);
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(deleteProjectQuery)) {
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                log.info("project repository: project deleted successfully");
                return "Project deleted successfully";
            } else {
                log.info("project repository: project not found for id = {}", id);
                return "Project not found";
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting project", e);
        }
    }

    private Project getProject(Project project, PreparedStatement preparedStatement) throws SQLException {
        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows > 0)
            return project;
        return null;
    }
}
