package pl.barpec12.tk1.flightserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Flight implements Serializable {
    private String operatingAirline, iataCode, aircraftModel, flightNumber, departureAirport, arrivalAirport, departureTerminal;
    private String arrivalTerminal, arrivalListOfGates, departureListOfGates, checkInLocation, checkInCounter, flightStatus;
    private ZonedDateTime scheduledArrival, scheduledDeparture, estimatedArrival, estimatedDeparture, checkInStart, checkInEnd, originDate;
}
