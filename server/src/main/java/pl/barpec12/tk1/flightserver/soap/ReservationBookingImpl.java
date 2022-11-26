package pl.barpec12.tk1.flightserver.soap;

import jakarta.jws.WebService;
import pl.barpec12.tk1.flightserver.FlightServer;
import pl.barpec12.tk1.flightserver.model.Flight;
import pl.barpec12.tk1.flightserver.model.Seat;

import java.util.HashSet;
import java.util.Set;

@WebService(endpointInterface = "pl.barpec12.tk1.flightserver.soap.ReservationBooking")
public class ReservationBookingImpl implements ReservationBooking {
    private FlightServer flightServer;
    public ReservationBookingImpl(FlightServer flightServer) {
        this.flightServer = flightServer;
    }

    @Override
    public Flight[] getFlights() {
        var list = flightServer.getFlights();
        return list.toArray(new Flight[list.size()]);
    }

    @Override
    public Seat[] getFreeSeats(Flight flight) {
        Set<Seat> seats = new HashSet<>(flight.getSeats());
        flightServer.getReservations().get(flight).forEach(reservation -> seats.remove(reservation.getSeat()));
        return seats.toArray(new Seat[seats.size()]);
    }
}
