package com.tracking.app.user;
import java.sql.SQLException;

public interface UserOperations {
    void createUser(String firstName, String lastName, String email, String password, int role) throws SQLException;
    void loginUser(String email, String password) throws SQLException;
}
