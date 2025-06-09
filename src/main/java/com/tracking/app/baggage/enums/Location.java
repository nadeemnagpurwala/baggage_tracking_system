package com.tracking.app.baggage.enums;
import lombok.Getter;

@Getter
public enum Location {
    CHECK_IN_AREA("Check In Area"),
    TRANSIT_AREA("Transit Area"),
    ARRIVAL_AREA("Arrival Area"),
    PICKUP_AREA("Pickup Area");

    private final String value;

    Location(String value) {
        this.value = value;
    }
}
