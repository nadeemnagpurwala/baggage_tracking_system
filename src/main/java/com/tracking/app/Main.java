package com.tracking.app;
import com.tracking.app.util.InputUtil;
import com.tracking.app.user.UserManagement;

public class Main {
    public static void main(String[] args) {
        UserManagement userManagement = new UserManagement();
        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            String choice = InputUtil.readOption("Choose an option: ");
            switch (choice) {
                case "1":
                    userManagement.register();
                    break;
                case "2":
                    userManagement.login();
                    break;
                case "3":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }
}
