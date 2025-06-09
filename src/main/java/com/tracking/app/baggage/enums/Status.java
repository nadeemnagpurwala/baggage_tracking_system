package com.tracking.app.baggage.enums;
import lombok.Getter;

@Getter
public enum Status {
    CHECKED_IN("Checked In"),
    IN_TRANSIT("In Transit"),
    ARRIVED("Arrived"),
    READY_FOR_PICKUP("Ready for Pickup"),
    CLAIMED("Claimed");

    private final String value;

    Status(String value) {
        this.value = value;
    }
}
