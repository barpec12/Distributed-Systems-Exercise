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
}
