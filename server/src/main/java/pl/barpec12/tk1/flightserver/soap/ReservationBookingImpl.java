package pl.barpec12.tk1.flightserver.soap;

import jakarta.jws.WebService;
import pl.barpec12.tk1.flightserver.ReservationService;
import pl.barpec12.tk1.flightserver.model.Flight;
import pl.barpec12.tk1.flightserver.model.Reservation;
import pl.barpec12.tk1.flightserver.model.Seat;


@WebService(endpointInterface = "pl.barpec12.tk1.flightserver.soap.ReservationBooking")
public class ReservationBookingImpl implements ReservationBooking {
    private ReservationService reservationService;
    public ReservationBookingImpl(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    public Flight[] getFlights() {
        return reservationService.getFlights();
    }

    @Override
    public Seat[] getFreeSeats(String flightNumber) {
        return reservationService.getFreeSeats(flightNumber);
    }

    /**
     *
     * @return true if reservation was successful
     */
    @Override
    public boolean reserveFlight(String flightNumber, Reservation reservation) {
        return reservationService.reserveFlight(flightNumber, reservation);
    }

    @Override
    public void addFlight(Flight flight) {
        reservationService.addFlight(flight);
    }

    @Override
    public void deleteFlight(String flightNumber) {
        reservationService.deleteFlight(flightNumber);
    }
}
