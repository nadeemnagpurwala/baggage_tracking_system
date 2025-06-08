package com.tracking.app.user;
import com.tracking.app.util.InputUtil;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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

    public Map<String, String>  login() {
        String email = InputUtil.readOption("Enter Email Address : ");
        isEmailAddressValid(email);
        String password = InputUtil.readOption("Enter Password : ");
        Map<String, String> result = userService.loginUser(email, password);
        Map<String, String> loginResponse = new HashMap<>();
        if ("success".equals(result.get("status"))) {
            System.out.println(result.get("message"));
            loginResponse.put("status", "success");
            loginResponse.put("role_id", result.get("role_id"));
            loginResponse.put("user_id", result.get("user_id"));
        } else {
            System.out.println("Login failed: " + result.get("message"));
            loginResponse.put("status", "error");
        }
        return loginResponse;
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
