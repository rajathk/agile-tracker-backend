package com.example.agiletracker.agile_tracker.service;

import com.example.agiletracker.agile_tracker.repository.FileUploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileUploadService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private FileUploadRepository fileUploadRepository;

    public String storeFile(MultipartFile file, int taskId) throws IOException {
        // Normalize file name
        //String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())) + UUID.randomUUID();
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // Generate unique file name
        //String uniqueFileName = UUID.randomUUID() + "_" + fileName;

        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        System.out.println("uploadPath = " + uploadPath);
        Files.createDirectories(uploadPath);

        // Copy file to the target location
        Path targetLocation = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        // Store file name in database
        try {
            //fileUploadRepository.saveFileReference(task, fileName, uploadDir, 1);
            fileUploadRepository.saveFileReference(taskId, fileName, targetLocation.toString(), 1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fileName;
    }

}
