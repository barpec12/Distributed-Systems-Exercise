package pl.barpec12.tk1.flightclient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seat {
    public enum SeatClass {
        FIRST, ECONOMY_PLUS, ECONOMY;
    }

    private SeatClass seatClass;
    private boolean isEmergencySeat;
    private int row;
    private char letter;
}
