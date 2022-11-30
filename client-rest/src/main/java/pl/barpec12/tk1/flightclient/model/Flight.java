package pl.barpec12.tk1.flightclient.model;

import lombok.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Flight implements Serializable {
    public enum AircraftModel {
        Boeing_737_900("Boeing 737-900"),
        Airbus_319("Airbus 319"),
        Embraer_E170("Embraer E170");

        private String name;

        AircraftModel(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
        public static AircraftModel fromName(String name) {
            return Arrays.stream(AircraftModel.values()).filter(model -> model.toString().equals(name)).findAny().get();
        }
    }

    private AircraftModel aircraftModel;
    private String operatingAirline, iataCode, flightNumber, departureAirport, arrivalAirport, departureTerminal;
    private String arrivalTerminal, arrivalListOfGates, departureListOfGates, checkInLocation, checkInCounter, flightStatus;
    @Getter
    private ZonedDateTime scheduledArrival, scheduledDeparture, estimatedArrival, estimatedDeparture, checkInStart, checkInEnd, originDate;

    private Set<Seat> seats;
}
