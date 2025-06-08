package com.tracking.app.baggage.service;
import com.tracking.app.baggage.model.Baggage;
import java.sql.SQLException;

public interface BaggageOperations {
    void createNewBaggage(Baggage baggage) throws SQLException;
}
