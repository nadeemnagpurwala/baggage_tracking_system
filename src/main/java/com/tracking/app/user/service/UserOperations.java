package com.tracking.app.user.service;
import java.sql.SQLException;
import java.util.Map;
import com.tracking.app.user.model.User;

public interface UserOperations {
    void createUser(User user) throws SQLException;
    Map<String, String> loginUser(String email, String password);
}
