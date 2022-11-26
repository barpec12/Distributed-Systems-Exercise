package pl.barpec12.tk1.flightserver.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pl.barpec12.tk1.flightserver.FlightServer;
import pl.barpec12.tk1.flightserver.model.Flight;

import java.util.List;

@Path("/reservation")
public class ReservationBookingController {
    private FlightServer flightServer;

    public ReservationBookingController() {
        this.flightServer = FlightServer.getFlightServer();
    }

    @GET
    @Path("/flights")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Flight> getFlights() {
        var list = flightServer.getFlights();
        return list;//list.toArray(new Flight[list.size()]);
    }

}
