package com.tracking.app.user;
import java.sql.SQLException;
import java.util.Map;

public interface UserOperations {
    void createUser(String firstName, String lastName, String email, String password, int role) throws SQLException;
    Map<String, String> loginUser(String email, String password);
}
