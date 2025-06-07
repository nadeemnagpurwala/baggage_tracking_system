package com.tracking.app.user;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import at.favre.lib.crypto.bcrypt.BCrypt;
import com.tracking.app.database.Config;

public abstract class AbstractUserService implements UserOperations {
    @Override
    public void createUser(String firstName, String lastName, String email, String password, int role) throws SQLException {
        try {
            String sql = "INSERT INTO users (first_name, last_name, email, password, role_id) VALUES (?, ?, ?, ?, ?)";
            String hashedPassword = this.encryptPassword(password);

            Connection connection = Config.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, hashedPassword);
            preparedStatement.setInt(5, role);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new SQLException("Email already exists.", e);
            } else {
                throw e;
            }
        }
    }

    @Override
    public Map<String, String> loginUser(String email, String password) {
        Map<String, String> response = new HashMap<>();
        try {
            String sql = "SELECT * FROM users WHERE email = ?";
            Connection connection = Config.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String storedHash = resultSet.getString("password");
                boolean isPasswordValid = this.validateUserPassword(password, storedHash);
                if (isPasswordValid) {
                    System.out.println("Hi " + resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
                    response.put("status", "success");
                    response.put("message", "User logged in successfully.");
                    response.put("role_id", resultSet.getString("role_id"));
                } else {
                    response.put("status", "error");
                    response.put("message", "User not found. Please verify again with proper credentials.");
                }
            } else {
                response.put("status", "error");
                response.put("message", "User not found. Please verify again with proper credentials.");
            }
        } catch (SQLException e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        return response;
    }

    private String encryptPassword(String password) {
        return BCrypt.withDefaults().hashToString(5, password.toCharArray());
    }

    private boolean validateUserPassword(String password, String storedHash) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), storedHash);
        return result.verified;
    }
}
