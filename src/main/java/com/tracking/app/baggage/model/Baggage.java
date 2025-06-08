package com.tracking.app.baggage.model;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Baggage {
    private String location;
    private String status;
    private int userId;
}
