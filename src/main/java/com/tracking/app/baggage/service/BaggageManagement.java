package com.tracking.app.baggage.service;
import com.tracking.app.baggage.model.Baggage;
import java.sql.SQLException;

public class BaggageManagement {
    private static final BaggageOperations baggageService = new BaggageService();
    public void checkInBaggage(Integer userId) {
        try {
            Baggage baggage = new Baggage("Check In Area", "Checked In", userId);
            baggageService.createNewBaggage(baggage);
        } catch (SQLException e) {
            System.out.println("Baggage check in failed: " + e.getMessage());
        }
    }

    public void getAllCheckedInBaggage() {
        try {
            String status = "Checked In";
            baggageService.getAllBaggageByStatus(status);
        } catch (SQLException e) {
            System.out.println("Checked in baggage retrieval failed: " + e.getMessage());
        }
    }

    public void getBaggageByBaggageId(Integer baggageId) {
        try {
            baggageService.getBaggageById(baggageId);
        } catch (SQLException e) {
            System.out.println("Baggage retrieval by the provided id failed: " + e.getMessage());
        }
    }

    public void getBaggageByBaggageIdForUser(Integer baggageId, Integer userId) {
        try {
            baggageService.getBaggageByIdAndUserId(baggageId, userId);
        } catch (SQLException e) {
            System.out.println("Baggage retrieval by the provided id failed: " + e.getMessage());
        }
    }

    public void updateBaggageStatus(Integer baggageId, String baggageStatus) {
        try {
            baggageService.baggageStatusUpdate(baggageId, baggageStatus);
        } catch (SQLException e) {
            System.out.println("Baggage status update for the provided id failed: " + e.getMessage());
        }
    }

    public void updateBaggageLocation(Integer baggageId, String baggageLocation) {
        try {
            baggageService.baggageLocationUpdate(baggageId, baggageLocation);
        } catch (SQLException e) {
            System.out.println("Baggage location update for the provided id failed: " + e.getMessage());
        }
    }
}
