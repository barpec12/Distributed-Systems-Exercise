package pl.barpec12.tk1.flightserver.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seat {
    public enum SeatClass {
        FIRST(3), ECONOMY_PLUS(2), ECONOMY(1);
        @Getter
        private double priceMultiplier;

        SeatClass(double priceMultiplier) {
            this.priceMultiplier = priceMultiplier;
        }
    }

    private SeatClass seatClass;
    private boolean isEmergencySeat;
    private int row;
    private char letter;
}
