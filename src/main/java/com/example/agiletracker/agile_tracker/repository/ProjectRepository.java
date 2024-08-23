package com.example.agiletracker.agile_tracker.repository;

import com.example.agiletracker.agile_tracker.constants.ProjectStatus;
import com.example.agiletracker.agile_tracker.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findByCreatedBy(int userId);
    List<Project> findByStatus(ProjectStatus status);

    @Query(value = "SELECT * FROM project WHERE start_date BETWEEN :startDate AND :endDate AND status = :status", nativeQuery = true)
    List<Project> findProjectByDateBetweenAndStatus(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("status") String status
    );
}
