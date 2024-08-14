package com.example.agiletracker.agile_tracker.controller;

import com.example.agiletracker.agile_tracker.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload/task/{taskId}")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable int taskId) throws IOException {
        String fileName = fileUploadService.storeFile(file, taskId);
        return ResponseEntity.ok("File uploaded successfully: " + fileName);
    }
}
