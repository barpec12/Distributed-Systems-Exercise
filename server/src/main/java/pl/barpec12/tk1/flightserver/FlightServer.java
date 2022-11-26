package pl.barpec12.tk1.flightserver;

import jakarta.xml.ws.Endpoint;
import lombok.Getter;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import pl.barpec12.tk1.flightserver.model.Flight;
import pl.barpec12.tk1.flightserver.rest.ReservationBookingController;
import pl.barpec12.tk1.flightserver.soap.ReservationBookingImpl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FlightServer {

	private static Logger logger = Logger.getLogger(FlightServer.class.getName());
	@Getter
	private List<Flight> flights = new ArrayList<>();

	@Getter
	private static FlightServer flightServer;
	protected FlightServer() {
		flights.add(Flight.builder().flightNumber("43223").build());

	}

	public static void main(String[] args) {
		flightServer = new FlightServer();
		Endpoint.publish("http://localhost:8090/ws/reservationBooking", new ReservationBookingImpl(flightServer));

		ResourceConfig resourceConfig = new ResourceConfig(ReservationBookingController.class);
		JdkHttpServerFactory.createHttpServer(URI.create("http://localhost:8080/"), resourceConfig, true);
	}

}
