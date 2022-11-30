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
    private boolean emergencySeat;
    private int row;
    private char letter;
    private double price;

    public void updatePrice(Flight flight) {
        //Cost depends on destination - the longer the name of the destination, the higher the price :)
        price = flight.getArrivalAirport().length() * seatClass.getPriceMultiplier();
    }

}
