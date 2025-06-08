package com.tracking.app.baggage.service;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
import com.tracking.app.database.Config;
import com.tracking.app.baggage.model.Baggage;

public abstract class AbstractBaggageService implements BaggageOperations{
    @Override
    public void createNewBaggage(Baggage baggage) throws SQLException {
        try {
            String sql = "INSERT INTO baggage (location, status, user_id) VALUES (?, ?, ?)";
            Connection connection = Config.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, baggage.getLocation());
            preparedStatement.setString(2, baggage.getStatus());
            preparedStatement.setInt(3, baggage.getUserId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Your baggage was not checked in successfully. Please try again.");
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long generatedId = generatedKeys.getLong(1);
                System.out.println("Your baggage was checked in successfully with the id " + generatedId);
            } else {
                throw new SQLException("Your baggage was not checked in successfully. Please try again.");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    public void getAllBaggagesByStatus(String status) throws SQLException {
        try {
            String sql = "SELECT * FROM baggage WHERE status = ?";
            Connection connection = Config.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // Map database columns to custom headers
                Map<String, String> columnHeaderMap = new LinkedHashMap<>();
                columnHeaderMap.put("id", "Claim ID");
                columnHeaderMap.put("location", "Location");
                columnHeaderMap.put("status", "Status");
                columnHeaderMap.put("user_id", "User ID");

                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                System.out.println("Below is the list of baggage found.");

                int colWidth = 20;

                // Print custom headers
                for (String dbCol : columnHeaderMap.keySet()) {
                    System.out.printf("%-" + colWidth + "s", columnHeaderMap.get(dbCol));
                }
                System.out.println();

                // Print rows
                while (resultSet.next()) {
                    for (String dbCol : columnHeaderMap.keySet()) {
                        Object value = resultSet.getObject(dbCol);
                        System.out.printf("%-" + colWidth + "s", value != null ? value.toString() : "NULL");
                    }
                    System.out.println();
                }
            } else {
                System.out.println("No baggage found for the provided status.");
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving baggage for the provided status" + e.getMessage());
        }
    }
}
