package pl.barpec12.tk1.flightserver.soap;

import jakarta.jws.WebService;
import pl.barpec12.tk1.flightserver.FlightServer;
import pl.barpec12.tk1.flightserver.model.Flight;
import pl.barpec12.tk1.flightserver.model.Reservation;
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
    public Seat[] getFreeSeats(String flightNumber) {
        var flightOptional = flightServer.getFlights().stream()
                .filter(f -> f.getFlightNumber().equals(flightNumber)).findAny();
        if(flightOptional.isEmpty()) return new Seat[0];
        var flight = flightOptional.get();
        Set<Seat> seats = new HashSet<>(flight.getSeats());
        flightServer.getReservations().stream().filter(r -> r.getFlightNumber().equals(flightNumber))
                .forEach(reservation -> seats.remove(reservation.getSeat()));
        return seats.toArray(new Seat[seats.size()]);
    }

    /**
     *
     * @return true if reservation was successful
     */
    @Override
    public boolean reserveFlight(String flightNumber, Reservation reservation) {
        var flightOptional = flightServer.getFlights().stream().filter(f -> f.getFlightNumber().equals(flightNumber)).findAny();
        if(flightOptional.isEmpty()) return false;
        var other = flightServer.getReservations()
                .stream().filter(r -> r.getFlightNumber().equals(flightNumber)
                        && r.getSeat().equals(reservation.getSeat())).findAny();
        if(other.isPresent()) return false;
        flightServer.addReservation(reservation);
        return true;
    }

    @Override
    public void addFlight(Flight flight) {
        var flights = flightServer.getFlights();
        flights.removeIf(f-> flight.getFlightNumber().equals(f.getFlightNumber()));
        flight.getSeats().forEach(seat -> seat.updatePrice(flight));
        flightServer.getFlights().add(flight);
    }

    @Override
    public void deleteFlight(String flightNumber) {
        var flights = flightServer.getFlights();
        flights.removeIf(f-> flightNumber.equals(f.getFlightNumber()));
    }
}
