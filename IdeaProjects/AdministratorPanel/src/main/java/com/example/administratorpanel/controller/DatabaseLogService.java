package com.example.administratorpanel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

@Service
public class DatabaseLogService {

    private final DataSource dataSource;

    @Autowired
    public DatabaseLogService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void saveLog(String level, String message) {
        String sql = "INSERT INTO log_table (timestamp, level, message) VALUES (?, ?, ?)";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setTimestamp(1, timestamp);
            preparedStatement.setString(2, level);
            preparedStatement.setString(3, message);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
