package com.tracking.app;
import com.tracking.app.baggage.service.BaggageManagement;
import com.tracking.app.util.InputUtil;
import com.tracking.app.user.service.UserManagement;
import java.util.Map;

public class Main {
    public static boolean loggedIn = false;
    public static void main(String[] args) {
        UserManagement userManagement = new UserManagement();
        while (!loggedIn) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            String choice = InputUtil.readOption("Choose an option: ");
            switch (choice) {
                case "1":
                    userManagement.register();
                    break;
                case "2":
                    Map<String, String> result = userManagement.login();
                    if ("success".equals(result.get("status"))) {
                        int roleId = Integer.parseInt(result.get("role_id"));
                        int userId = Integer.parseInt(result.get("user_id"));
                        loggedIn = true;
                        baggageOptions(roleId, userId);
                    }
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

    private static void baggageOptions(int roleId, int userId) {
        BaggageManagement baggageManagement = new BaggageManagement();
        while(loggedIn) {
            if (roleId == 1) {
                System.out.println("1. View All Checked In Baggage");
                System.out.println("2. Get Baggage Status");
                System.out.println("3. Update Baggage Status and Location");
                System.out.println("4. Delete Claimed Baggage Record");
                System.out.println("5. Logout");
                String choice = InputUtil.readOption("Choose an option: ");
                int baggageId;
                switch (choice) {
                    case "1":
                        baggageManagement.getAllCheckedInBaggage();
                        break;
                    case "2":
                        baggageId = Integer.parseInt(InputUtil.readOption("Enter baggage id: "));
                        baggageManagement.getBaggageByBaggageId(baggageId);
                        break;
                    case "3":
                        baggageId = Integer.parseInt(InputUtil.readOption("Enter baggage id: "));
                        String baggageStatus = InputUtil.readOption("Select a baggage status from Checked In, In Transit, Arrived, Ready for Pickup, Claimed: ");
                        String baggageLocation = InputUtil.readOption("Select a baggage location from Check In Area, Transit Area, Arrival Area, Pickup Area: ");
                        baggageManagement.updateBaggageStatusAndLocation(baggageId, baggageStatus, baggageLocation);
                        break;
                    case "4":
                        baggageId = Integer.parseInt(InputUtil.readOption("Enter baggage id: "));
                        baggageManagement.deleteClaimedBaggageRecord(baggageId);
                        break;
                    case "5":
                        loggedIn = false;
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            } else if (roleId == 2) {
                System.out.println("1. Check In Baggage");
                System.out.println("2. Get Baggage Information");
                System.out.println("3. Logout");
                String choice = InputUtil.readOption("Choose an option: ");
                switch (choice) {
                    case "1":
                        baggageManagement.checkInBaggage(userId);
                        break;
                    case "2":
                        int baggageId = Integer.parseInt(InputUtil.readOption("Enter baggage id: "));
                        baggageManagement.getBaggageByBaggageIdForUser(baggageId, userId);
                    case "3":
                        loggedIn = false;
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            } else {
                System.out.println("Invalid role provided");
            }
        }
    }
}
