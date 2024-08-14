package com.example.agiletracker.agile_tracker.repository;

import com.example.agiletracker.agile_tracker.entity.Audit;
import com.google.gson.JsonObject;
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
public class AuditRepository {

    @Autowired
    private DataSource dataSource;

    @Value("${audit.create.query}")
    private String createAuditQuery;

    @Value("${audit.read.all.query}")
    private String readAllAuditQuery;

    public void createAudit(Audit audit){
        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(createAuditQuery)){
            preparedStatement.setInt(1, audit.getUser_id());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(audit.getCreated_on()));
            preparedStatement.setString(3, audit.getEvent().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Audit> readAllAudit(){
        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(readAllAuditQuery)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Audit> audits = new ArrayList<>();
            while (resultSet.next()){
                Audit audit = new Audit();
                audit.setUser_id(resultSet.getInt("user_id"));
                audit.setCreated_on(resultSet.getTimestamp("created_on").toLocalDateTime());
                audit.setEvent(resultSet.getObject("event", JsonObject.class));
                audits.add(audit);
            }
            return audits;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
