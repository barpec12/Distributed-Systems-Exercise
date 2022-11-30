package pl.barpec12.tk1.flightserver.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.barpec12.tk1.flightserver.FlightServer;
import pl.barpec12.tk1.flightserver.ReservationService;
import pl.barpec12.tk1.flightserver.model.Flight;
import pl.barpec12.tk1.flightserver.model.Reservation;
import pl.barpec12.tk1.flightserver.model.Seat;

@Path("/reservation")
public class ReservationBookingController {
    private ReservationService reservationService;

    public ReservationBookingController() {
        this.reservationService = FlightServer.getReservationService();
    }

    @GET
    @Path("/flight")
    @Produces(MediaType.APPLICATION_JSON)
    public Flight[] getFlights() {
        return reservationService.getFlights();
    }

    @GET
    @Path("/flight/{flightNumber}/free-seats")
    @Produces(MediaType.APPLICATION_JSON)
    public Seat[] getFreeSeats(@PathParam("flightNumber") String flightNumber) {
        return reservationService.getFreeSeats(flightNumber);
    }

    /**
     *
     * @return true if reservation was successful
     */
    @POST
    @Path("/flight/{flightNumber}/reserve")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response reserveFlight(@PathParam("flightNumber") String flightNumber, Reservation reservation) {
        if(reservationService.reserveFlight(flightNumber, reservation))
            return Response.status(Response.Status.OK).build();
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    @POST
    @Path("/flight")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addFlight(Flight flight) {
        reservationService.addFlight(flight);
    }

    @DELETE
    @Path("/flight/{flightNumber}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteFlight(String flightNumber) {
        reservationService.deleteFlight(flightNumber);
    }


}
