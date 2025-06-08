package com.tracking.app.baggage.service;
import java.sql.*;
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
}
