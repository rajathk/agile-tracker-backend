package com.example.agiletracker.agile_tracker.service;

import com.example.agiletracker.agile_tracker.entity.Audit;
import com.example.agiletracker.agile_tracker.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuditService {

    @Autowired
    private AuditRepository auditRepository;

    public void createAudit(Audit audit){
        auditRepository.createAudit(audit);
    }

}
