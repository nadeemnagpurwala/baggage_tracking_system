package com.tracking.app.baggage.service;
import java.sql.*;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import com.tracking.app.database.Config;
import com.tracking.app.baggage.model.Baggage;

public abstract class AbstractBaggageService implements BaggageOperations {

    @Override
    public void createNewBaggage(Baggage baggage) throws SQLException {
        String sql = "INSERT INTO baggage (location, status, user_id) VALUES (?, ?, ?)";
        try (
            Connection connection = Config.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, baggage.getLocation());
            preparedStatement.setString(2, baggage.getStatus());
            preparedStatement.setInt(3, baggage.getUserId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Your baggage was not checked in successfully. Please try again.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long generatedId = generatedKeys.getLong(1);
                    System.out.println("Your baggage was checked in successfully with the id " + generatedId);
                } else {
                    throw new SQLException("Your baggage was not checked in successfully. Please try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error during check-in for your baggage: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void getAllBaggageByStatus(String status) throws SQLException {
        String sql = "SELECT * FROM baggage WHERE status = ?";
        try (
            Connection connection = Config.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, status);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                this.prepareResultForDisplay(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving baggage for the provided status: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void getBaggageById(Integer id) throws SQLException {
        String sql = "SELECT * FROM baggage WHERE id = ?";
        try (
            Connection connection = Config.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                this.prepareResultForDisplay(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving baggage for the provided id: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void getBaggageByIdAndUserId(Integer id, Integer userId) throws SQLException {
        String sql = "SELECT status, location FROM baggage WHERE id = ? AND user_id = ?";
        try (
            Connection connection = Config.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                this.prepareResultForDisplay(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving baggage for the provided id and user ID: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void baggageStatusAndLocationUpdate(Integer id, String baggageStatus, String baggageLocation) throws SQLException {
        String sql = "UPDATE baggage SET status = ?, location = ? WHERE id = ?";
        try (
            Connection connection = Config.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, baggageStatus);
            preparedStatement.setString(2, baggageLocation);
            preparedStatement.setInt(3, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("The baggage status and location update failed. Please try again.");
            }
            else {
                System.out.println("The baggage status and location was updated successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error during baggage status and location update: " + e.getMessage());
            throw e;
        }
    }

    public void deleteClaimedBaggage(Integer baggageId) throws SQLException{
        String sql = "DELETE FROM baggage WHERE id = ? AND status = ?";
        try (
            Connection connection = Config.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, baggageId);
            preparedStatement.setString(2, "Claimed");
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("The baggage deletion failed as the status is not yet claimed or the baggage does not exists. Please try again later.");
            }
            else {
                System.out.println("The baggage was deleted successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error during deleting baggage: " + e.getMessage());
        }
    }

    private void prepareResultForDisplay(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            System.out.println("No baggage found for the provided criteria.");
            return;
        }

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Create a set of actual column names present in the ResultSet
        Set<String> availableColumns = new HashSet<>();
        for (int i = 1; i <= columnCount; i++) {
            availableColumns.add(metaData.getColumnLabel(i).toLowerCase());
        }

        Map<String, String> filteredHeaderMap = getFilteredHeaderMap(availableColumns);

        System.out.println("Below is the list of baggage found:");
        int colWidth = 20;

        // Print custom headers
        for (String header : filteredHeaderMap.values()) {
            System.out.printf("%-" + colWidth + "s", header);
        }
        System.out.println();
        System.out.println("-".repeat(colWidth * filteredHeaderMap.size()));

        // Print data rows
        do {
            for (String column : filteredHeaderMap.keySet()) {
                Object value = resultSet.getObject(column);
                System.out.printf("%-" + colWidth + "s", value != null ? value.toString() : "NULL");
            }
            System.out.println();
        } while (resultSet.next());
    }

    private Map<String, String> getFilteredHeaderMap(Set<String> availableColumns) {
        Map<String, String> columnHeaderMap = new LinkedHashMap<>();
        columnHeaderMap.put("id", "Claim ID");
        columnHeaderMap.put("location", "Location");
        columnHeaderMap.put("status", "Status");
        columnHeaderMap.put("user_id", "User ID");

        Map<String, String> filteredHeaderMap = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : columnHeaderMap.entrySet()) {
            if (availableColumns.contains(entry.getKey())) {
                filteredHeaderMap.put(entry.getKey(), entry.getValue());
            }
        }
        return filteredHeaderMap;
    }
}
