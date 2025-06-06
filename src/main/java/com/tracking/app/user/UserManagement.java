package com.tracking.app.user;
import com.tracking.app.util.InputUtil;

import java.sql.SQLException;

public class UserManagement {
    private static final UserOperations userService = new UserService();

    public void register() {
        String firstName = InputUtil.readOption("Enter First Name : ");
        String lastName = InputUtil.readOption("Enter Last Name : ");
        String email = InputUtil.readOption("Enter Email Address : ");
        isEmailAddressValid(email);
        String password = InputUtil.readOption("Enter Password : ");
        int userRole = Integer.parseInt(InputUtil.readOption("Enter User Role. Specify 1 for admin and 2 for user : "));
        isUserRoleValid(userRole);
        try {
            userService.createUser(firstName, lastName, email, password, userRole);
            System.out.println("User registered successfully. You can now proceed to login");
        } catch (SQLException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    public void login() {
        String email = InputUtil.readOption("Enter Email Address : ");
        isEmailAddressValid(email);
        String password = InputUtil.readOption("Enter Password : ");
        try {
            userService.loginUser(email, password);
            System.out.println("User logged in successfully.");
        } catch (SQLException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    private void isEmailAddressValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException("Invalid Email Address");
        }
    }

    private void isUserRoleValid(int userRole) {
        if (userRole != 1 && userRole != 2) {
            throw new IllegalArgumentException("Invalid Role");
        }
    }
}
