package pl.barpec12.tk1.flightclient.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import pl.barpec12.tk1.flightclient.model.Flight;
import pl.barpec12.tk1.flightclient.model.Reservation;
import pl.barpec12.tk1.flightclient.model.Seat;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ReservationBooking {
    @WebMethod
    Flight[] getFlights();
    @WebMethod
    Seat[] getFreeSeats(String flightNumber);
    @WebMethod
    boolean reserveFlight(String flightNumber, Reservation reservation);
    @WebMethod
    void addFlight(Flight flight);
}