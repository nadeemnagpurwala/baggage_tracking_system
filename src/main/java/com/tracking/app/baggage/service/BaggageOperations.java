package com.tracking.app.baggage.service;
import com.tracking.app.baggage.model.Baggage;
import java.sql.SQLException;

public interface BaggageOperations {
    void createNewBaggage(Baggage baggage) throws SQLException;
    void getAllBaggageByStatus(String status) throws SQLException;
    void getBaggageById(Integer id) throws SQLException;
    void getBaggageByIdAndUserId(Integer id, Integer userId) throws SQLException;
    void baggageStatusAndLocationUpdate(Integer id, String baggageStatus, String baggageLocation) throws SQLException;
    void deleteClaimedBaggage(Integer id) throws SQLException;
}
