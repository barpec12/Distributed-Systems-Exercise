package pl.barpec12.tk1.flightclient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seat implements Serializable {
    public enum SeatClass {
        FIRST, ECONOMY_PLUS, ECONOMY;
    }

    private SeatClass seatClass;
    private boolean emergencySeat;
    private int row;
    private char letter;
    private double price;
}
