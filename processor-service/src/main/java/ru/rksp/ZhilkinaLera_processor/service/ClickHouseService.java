package ru.rksp.ZhilkinaLera.processor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDateTime;

@Service
public class ClickHouseService {

    private static final Logger logger = LoggerFactory.getLogger(ClickHouseService.class);

    @Value("${clickhouse.jdbc-url}")
    private String jdbcUrl;

    @Value("${clickhouse.username}")
    private String username;

    @Value("${clickhouse.password}")
    private String password;

    public void saveCountToClickHouse(long count) {
        String sql = "INSERT INTO агрегаты_событий_платежей (дата_и_время_записи, количество_записей) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, LocalDateTime.now());
            statement.setLong(2, count);

            int rowsAffected = statement.executeUpdate();
            logger.info("Saved count {} to ClickHouse. Rows affected: {}", count, rowsAffected);

        } catch (SQLException e) {
            logger.error("Error saving to ClickHouse: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save to ClickHouse", e);
        }
    }

    // Метод для проверки соединения
    public boolean testConnection() {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            return connection.isValid(2);
        } catch (SQLException e) {
            logger.error("ClickHouse connection test failed: {}", e.getMessage());
            return false;
        }
    }
}