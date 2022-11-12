package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Flight {
    private String operatingAirline, iataCode, trackingNumber, departure, arrival, terminal;
    private ZonedDateTime scheduledDeparture, estimatedDeparture;
}
