package pl.barpec12.tk1.flightserver.model;

import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.*;
import pl.barpec12.tk1.flightserver.soap.ZonedDateTimeAdapter;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
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
            return Arrays.stream(AircraftModel.values()).filter(model -> model.name().equals(name)).findAny().get();
        }
    }

    public static FlightBuilder builder() {
        return new CustomFlightBuilder();
    }

    public static class CustomFlightBuilder extends FlightBuilder{

        public FlightBuilder aircraftModel(AircraftModel airCraftModel) {
            super.aircraftModel(airCraftModel);
            switch (airCraftModel) {
                case Boeing_737_900 -> {
                    seats(prepareBoeing());
                }
                case Airbus_319 -> {
                    seats(prepareAirbus());
                }
                case Embraer_E170 -> {
                    seats(prepareEmbraer());
                }
            }
            return this;
        }
        @Override
        public Flight build() {
            Flight f = super.build();
            f.getSeats().forEach(seat -> seat.updatePrice(f));
            return f;
        }
    }

    public void setAircraftModel(AircraftModel airCraftModel) {
        this.aircraftModel = airCraftModel;
        switch (airCraftModel) {
            case Boeing_737_900 -> {
                seats = prepareBoeing();
            }
            case Airbus_319 -> {
                seats = prepareAirbus();
            }
            case Embraer_E170 -> {
                seats = prepareEmbraer();
            }
        }
        seats.forEach(seat -> seat.updatePrice(this));
    }

    private AircraftModel aircraftModel;
    private String operatingAirline, iataCode, flightNumber, departureAirport, arrivalAirport, departureTerminal;
    private String arrivalTerminal, arrivalListOfGates, departureListOfGates, checkInLocation, checkInCounter, flightStatus;
    @Getter(onMethod = @__({@XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)}))
    private ZonedDateTime scheduledArrival, scheduledDeparture, estimatedArrival, estimatedDeparture, checkInStart, checkInEnd, originDate;

    private Set<Seat> seats;

    private static Set<Seat> prepareBoeing() {
        Set<Seat> seats = new HashSet<>();
        Seat.SeatBuilder seatBuilder = Seat.builder();
        seatBuilder.seatClass(Seat.SeatClass.FIRST);
        for(int i = 1; i<6; i++) {
            for(int j = 1; j<3; j++) {
                seats.add(seatBuilder.row(i).letter(letter(j)).build());
            }
            for(int j = 3; j<5; j++) {
                seats.add(seatBuilder.row(i).letter(letter(j+2)).build());
            }
        }
        seatBuilder.seatClass(Seat.SeatClass.ECONOMY_PLUS);
        for(int i = 8; i<12; i++) {
            for(int j = 1; j<7; j++) {
                seats.add(seatBuilder.row(i).letter(letter(j)).build());
            }
        }

        seatBuilder.row(7);
        seats.add(seatBuilder.letter('D').build());
        seats.add(seatBuilder.letter('E').build());
        seats.add(seatBuilder.letter('F').build());
        //
        seatBuilder.row(12);
        seats.add(seatBuilder.letter('A').build());
        seats.add(seatBuilder.letter('B').build());
        seats.add(seatBuilder.letter('C').build());
        seatBuilder.seatClass(Seat.SeatClass.ECONOMY);
        seats.add(seatBuilder.letter('D').build());
        seats.add(seatBuilder.letter('E').build());
        seats.add(seatBuilder.letter('F').build());
        //
        for(int i = 14; i<16; i++) {
            seatBuilder.row(i);
            for(int j = 1; j<7; j++) {
                seats.add(seatBuilder.letter(letter(j)).build());
            }
        }
        seatBuilder.seatClass(Seat.SeatClass.ECONOMY_PLUS);
        seatBuilder.emergencySeat(true);
        for(int i = 20; i<22; i++) {
            seatBuilder.row(i);
            for(int j = 1; j<7; j++) {
                seats.add(seatBuilder.letter(letter(j)).build());
            }
        }
        seatBuilder.emergencySeat(false);
        seatBuilder.seatClass(Seat.SeatClass.ECONOMY);
        for(int i = 22; i<40; i++) {
            seatBuilder.row(i);
            for(int j = 1; j<7; j++) {
                seats.add(seatBuilder.letter(letter(j)).build());
            }
        }

        return seats;
    }

    private static Set<Seat> prepareAirbus() {
        Set<Seat> seats = new HashSet<>();
        Seat.SeatBuilder seatBuilder = Seat.builder();
        seatBuilder.seatClass(Seat.SeatClass.FIRST);
        for(int i = 1; i<3; i++) {
            for(int j = 1; j<3; j++) {
                seats.add(seatBuilder.row(i).letter(letter(j)).build());
            }
            for(int j = 3; j<5; j++) {
                seats.add(seatBuilder.row(i).letter(letter(j+2)).build());
            }
        }

        seatBuilder.seatClass(Seat.SeatClass.ECONOMY_PLUS);
        var rows = Set.of(7,8,10,11,12,20);
        for(int i : rows) {
            for(int j = 1; j<7; j++) {
                seats.add(seatBuilder.row(i).letter(letter(j)).build());
            }
        }
        seatBuilder.emergencySeat(true);
        for(int j = 1; j<7; j++) {
            seats.add(seatBuilder.row(21).letter(letter(j)).build());
        }
        seatBuilder.emergencySeat(false);
        seatBuilder.seatClass(Seat.SeatClass.ECONOMY);

        for(int i = 22; i < 36; i++) {
            for(int j = 1; j<7; j++) {
                seats.add(seatBuilder.row(i).letter(letter(j)).build());
            }
        }

        return seats;
    }

    private static Set<Seat> prepareEmbraer() {
        Set<Seat> seats = new HashSet<>();
        Seat.SeatBuilder seatBuilder = Seat.builder();
        seatBuilder.seatClass(Seat.SeatClass.FIRST);
        for(int i = 1; i<3; i++) {
            seats.add(seatBuilder.row(i).letter('A').build());
            seats.add(seatBuilder.row(i).letter('C').build());
            seats.add(seatBuilder.row(i).letter('D').build());
        }
        seatBuilder.seatClass(Seat.SeatClass.ECONOMY_PLUS);
        for(int i = 7; i < 11; i++) {
            for(int j = 1; j<5; j++) {
                seats.add(seatBuilder.row(i).letter(letter(j)).build());
            }
        }
        seatBuilder.seatClass(Seat.SeatClass.ECONOMY);
        for(int i = 11; i < 25; i++) {
            for(int j = 1; j<5; j++) {
                seats.add(seatBuilder.row(i).letter(letter(j)).build());
            }
        }
        return seats;
    }

    private static char letter(int number) {
        return (char) (number + 64);
    }

}
