package pl.barpec12.tk1.flightserver;

import jakarta.xml.ws.Endpoint;
import lombok.Getter;
import pl.barpec12.tk1.flightserver.model.Flight;
import pl.barpec12.tk1.flightserver.soap.ReservationBookingImpl;

import java.util.ArrayList;
import java.util.logging.Logger;

public class FlightServer {

	private static Logger logger = Logger.getLogger(FlightServer.class.getName());
	@Getter
	private ArrayList<Flight> flights = new ArrayList<>();

	protected FlightServer() {
		super();

	}

	public static void main(String[] args) {
		var flightServer = new FlightServer();
		flightServer.flights.add(Flight.builder().flightNumber("43223").build());
		Endpoint.publish("http://localhost:9999/ws/reservationBooking", new ReservationBookingImpl(flightServer));
	}

}
