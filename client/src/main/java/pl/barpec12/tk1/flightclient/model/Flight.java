package pl.barpec12.tk1.flightclient.model;

import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.*;
import pl.barpec12.tk1.flightclient.soap.ZonedDateTimeAdapter;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Flight implements Serializable {
    private String operatingAirline, iataCode, aircraftModelNameComboBox, flightNumber, departureAirport, arrivalAirport, departureTerminal;
    private String arrivalTerminal, arrivalListOfGates, departureListOfGates, checkInLocation, checkInCounter, flightStatus;
    @Getter(onMethod = @__({@XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)}))
    private ZonedDateTime scheduledArrival, scheduledDeparture, estimatedArrival, estimatedDeparture, checkInStart, checkInEnd, originDate;

    private Set<Seat> seats;
}
