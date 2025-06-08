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
}
