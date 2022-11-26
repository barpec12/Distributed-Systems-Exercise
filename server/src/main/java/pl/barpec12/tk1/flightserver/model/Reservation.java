package pl.barpec12.tk1.flightserver.model;

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
    private Seat seat;
    private Meal meal;
}
