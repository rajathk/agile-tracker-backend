package com.example.agiletracker.agile_tracker.repository;

import com.example.agiletracker.agile_tracker.constants.TaskStatus;
import com.example.agiletracker.agile_tracker.entity.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByProjectId(int projectId);

    List<Task> findByProjectIdAndStatus(int projectId, TaskStatus status);
}

//@Slf4j
//@Repository
//public class TaskRepository {
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Value("${task.read.query}")
//    private String getTaskQuery;
//
//    @Value("${task.read.all.query}")
//    private String getAllTasksQuery;
//
//    @Value("${task.create.query}")
//    private String createTaskQuery;
//
//    @Value("${task.update.query}")
//    private String updateTaskQuery;
//
//    @Value("${task.delete.query}")
//    private String deleteTaskQuery;
//
//    public Task getTask(int id){
//        log.info("task repository: get task request received for id = {}", id);
//        try (Connection connection = dataSource.getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement(getTaskQuery)){
//            preparedStatement.setLong(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()){
//                Task task = new Task();
//                task.setId(resultSet.getInt("id"));
//                task.setProj_id(resultSet.getInt("proj_id"));
//                task.setTitle(resultSet.getString("title"));
//                task.setDescription(resultSet.getString("description"));
//                task.setAssignee(resultSet.getInt("assignee"));
//                task.setPriority(resultSet.getString("priority"));
//                task.setStatus(resultSet.getString("status"));
//                task.setDue_date(resultSet.getTimestamp("due_date").toLocalDateTime());
//                task.setCreated_by(resultSet.getInt("created_by"));
//                task.setCreated_on(resultSet.getTimestamp("created_on").toLocalDateTime());
//                task.setModified_by(resultSet.getInt("modified_by"));
//                task.setModified_on(resultSet.getTimestamp("modified_on").toLocalDateTime());
//                return task;
//            } else {
//                log.info("task repository: no task found for id = {}", id);
//                return null;
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public List<Task> getAllTasks(int projectId){
//        log.info("task repository: get all tasks request received");
//        try (Connection connection = dataSource.getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement(getAllTasksQuery)){
//            preparedStatement.setInt(1, projectId);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            List<Task> tasks = new ArrayList<>();
//            while (resultSet.next()) {
//                Task task = new Task();
//                task.setId(resultSet.getInt("id"));
//                task.setProj_id(resultSet.getInt("proj_id"));
//                task.setTitle(resultSet.getString("title"));
//                task.setDescription(resultSet.getString("description"));
//                task.setAssignee(resultSet.getInt("assignee"));
//                task.setPriority(resultSet.getString("priority"));
//                task.setStatus(resultSet.getString("status"));
//                task.setDue_date(resultSet.getTimestamp("due_date").toLocalDateTime());
//                task.setCreated_by(resultSet.getInt("created_by"));
//                task.setCreated_on(resultSet.getTimestamp("created_on").toLocalDateTime());
//                task.setModified_by(resultSet.getInt("modified_by"));
//                task.setModified_on(resultSet.getTimestamp("modified_on").toLocalDateTime());
//                tasks.add(task);
//            }
//            return tasks;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public Task create(Task task){
//        log.info("task repository: create task request received for task = {}", task);
//        try (Connection connection = dataSource.getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement(createTaskQuery)){
//            preparedStatement.setInt(1, task.getProj_id());
//            preparedStatement.setString(2, task.getTitle());
//            preparedStatement.setString(3, task.getDescription());
//            preparedStatement.setInt(4, task.getAssignee());
//            preparedStatement.setString(5, task.getPriority());
//            preparedStatement.setString(6, task.getStatus());
//            preparedStatement.setTimestamp(7, Timestamp.valueOf(task.getDue_date()));
//            preparedStatement.setInt(8, task.getCreated_by());
//            preparedStatement.setTimestamp(9, Timestamp.valueOf(task.getCreated_on()));
//            preparedStatement.setInt(10, task.getModified_by());
//            preparedStatement.setTimestamp(11, Timestamp.valueOf(task.getModified_on()));
//            return getTask(task, preparedStatement);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public Task update(int id, Task task){
//        log.info("task repository: update task request received for task = {}", task);
//        try (Connection conn = dataSource.getConnection();
//             PreparedStatement preparedStatement = conn.prepareStatement(updateTaskQuery)) {
//            preparedStatement.setString(1, task.getTitle());
//            preparedStatement.setString(2, task.getDescription());
//            preparedStatement.setInt(3, task.getAssignee());
//            preparedStatement.setString(4, task.getPriority());
//            preparedStatement.setString(5, task.getStatus());
//            preparedStatement.setTimestamp(6, Timestamp.valueOf(task.getDue_date()));
//            preparedStatement.setInt(7, task.getModified_by());
//            preparedStatement.setLong(8, id);
//            return getTask(task, preparedStatement);
//        } catch (SQLException e) {
//            throw new RuntimeException("Error updating task", e);
//        }
//    }
//
//    public String delete(int id){
//        log.info("task repository: delete task request received for id = {}", id);
//        try (Connection conn = dataSource.getConnection();
//             PreparedStatement preparedStatement = conn.prepareStatement(deleteTaskQuery)) {
//            preparedStatement.setLong(1, id);
//            int affectedRows = preparedStatement.executeUpdate();
//            if (affectedRows > 0) {
//                return "Task deleted successfully";
//            } else {
//                return "Task not found";
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Error deleting task", e);
//        }
//    }
//
//    private Task getTask(Task task, PreparedStatement preparedStatement) throws SQLException {
//        int affectedRows = preparedStatement.executeUpdate();
//        if (affectedRows > 0)
//            return task;
//        return null;
//    }
//}
