package pl.barpec12.tk1.flightclient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
