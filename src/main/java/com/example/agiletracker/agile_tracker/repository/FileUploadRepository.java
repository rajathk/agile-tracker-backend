package com.example.agiletracker.agile_tracker.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

@Repository
public class FileUploadRepository {

    @Autowired
    private DataSource dataSource;

    @Value("${task.file.upload.query}")
    private String uploadFileQuery;


    public void saveFileReference(int taskId, String fileName, String filePath, int uploadedBy) throws SQLException {

        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(uploadFileQuery)) {
            preparedStatement.setInt(1, taskId);
            preparedStatement.setString(2, fileName);
            preparedStatement.setString(3, filePath);
            preparedStatement.setInt(4, uploadedBy);
            preparedStatement.executeUpdate();
        }
    }

}
