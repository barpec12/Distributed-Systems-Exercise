package pl.barpec12.tk1.flightserver.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation {
    public enum Meal {
        STANDARD, VEGETARIAN, VEGAN;
    }
    private String customerId;
    private Seat seat;
    private Meal meal;
    private String flightNumber;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String destination;

    public Reservation(Flight flight) {
        this.flightNumber = flight.getFlightNumber();
        this.destination = flight.getArrivalAirport();
    }

    public double getPrice() {
        //Cost depends on destination - the longer the name of the destination, the higher the price :)
        return destination.length() * seat.getSeatClass().getPriceMultiplier();
    }
}
