package pl.barpec12.tk1.flightclient.rest;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.barpec12.tk1.flightclient.model.Flight;
import pl.barpec12.tk1.flightclient.model.Reservation;
import pl.barpec12.tk1.flightclient.model.Seat;

import java.util.List;

public class ReservationBookingImpl implements ReservationBooking{
    private WebTarget webTarget;
    public ReservationBookingImpl(WebTarget webTarget) {
        this.webTarget = webTarget;
    }

    @Override
    public Flight[] getFlights() {
        WebTarget flightsList = webTarget.path("/flight");
        return flightsList.request(MediaType.APPLICATION_JSON).get(Flight[].class);
    }

    @Override
    public Seat[] getFreeSeats(String flightNumber) {
        WebTarget localTarget = webTarget.path("/flight/"+flightNumber+"/free-seats");
        return localTarget.request(MediaType.APPLICATION_JSON).get(Seat[].class);
    }

    @Override
    public boolean reserveFlight(String flightNumber, Reservation reservation) {
        WebTarget localTarget = webTarget.path("/flight/"+flightNumber+"/reserve");
        Response response = localTarget.request(MediaType.APPLICATION_JSON).post(Entity.entity(reservation, MediaType.APPLICATION_JSON));
        return response.getStatus() == Response.Status.OK.getStatusCode();
    }

    @Override
    public void addFlight(Flight flight) {
        WebTarget localTarget = webTarget.path("/flight");
        localTarget.request().post(Entity.entity(flight, MediaType.APPLICATION_JSON));
    }

    @Override
    public void deleteFlight(String flightNumber) {
        WebTarget localTarget = webTarget.path("/flight/"+flightNumber);
        localTarget.request().delete();
    }
}
