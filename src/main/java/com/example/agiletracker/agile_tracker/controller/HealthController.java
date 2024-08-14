package com.example.agiletracker.agile_tracker.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HealthController {

    @Operation(summary = "Health Check", description = "Returns OK if server is up and running")
    @GetMapping("/health")
    public String health() {
        log.info("health controller: health check request received");
        return "OK";
    }
}
