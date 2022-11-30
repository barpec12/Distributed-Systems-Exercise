package pl.barpec12.tk1.flightclient.rest;

import pl.barpec12.tk1.flightclient.model.Flight;
import pl.barpec12.tk1.flightclient.model.Reservation;
import pl.barpec12.tk1.flightclient.model.Seat;


public interface ReservationBooking {
    Flight[] getFlights();
    Seat[] getFreeSeats(String flightNumber);
    boolean reserveFlight(String flightNumber, Reservation reservation);
    void addFlight(Flight flight);
    void deleteFlight(String flightNumber);
}