package pl.barpec12.tk1.flightserver;

import jakarta.xml.ws.Endpoint;
import lombok.Getter;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import pl.barpec12.tk1.flightserver.model.Flight;
import pl.barpec12.tk1.flightserver.model.Reservation;
import pl.barpec12.tk1.flightserver.rest.ReservationBookingController;
import pl.barpec12.tk1.flightserver.soap.ReservationBookingImpl;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.logging.Logger;

public class FlightServer {

	private static Logger logger = Logger.getLogger(FlightServer.class.getName());
	@Getter
	private List<Flight> flights = new ArrayList<>();
	@Getter
	private Set<Reservation> reservations = new HashSet<>();

	@Getter
	private static FlightServer flightServer;
	@Getter
	private static ReservationService reservationService;

	private Random random;
	protected FlightServer() {
		random = new Random();

		var flightBuilder = Flight.builder();
		for(int i = 0; i<100; i++) {
			flights.add(prepareRandomFlight(flightBuilder));
		}
	}

	public static void main(String[] args) {
		flightServer = new FlightServer();
		reservationService = new ReservationService(flightServer);
		Endpoint.publish("http://localhost:8090/ws/reservationBooking", new ReservationBookingImpl(reservationService));

		ResourceConfig resourceConfig = new ResourceConfig(ReservationBookingController.class);
		JdkHttpServerFactory.createHttpServer(URI.create("http://localhost:8080/"), resourceConfig, true);
	}

	public void addReservation(Reservation reservation) {
		reservations.add(reservation);
	}

	public void addFlight(Flight flight) {
		flights.add(flight);
	}

	private ZonedDateTime randomDate() {
		return ZonedDateTime.now().plusMinutes(random.nextInt(10000));
	}

	private final List<String> departureAirports = List.of("Darmstadt", "Frankfurt", "Barcelona", "Budapest", "Copenhagen", "London");
	private final List<String> arrivalAirports = List.of("Warsaw", "Berlin", "Paris", "Amsterdam", "Dubai", "Kiev", "Lisbon");
	private final List<String> operatingAirlines = List.of("Lufthansa", "Lot", "Ryanair", "Wizz Air", "EasyJet");
	private Flight prepareRandomFlight(Flight.FlightBuilder flightBuilder) {
		var airline = operatingAirlines.get(random.nextInt(operatingAirlines.size()));
		return flightBuilder
				.flightNumber(String.valueOf(random.nextInt(1000000)))
				.originDate(randomDate())
				.estimatedArrival(randomDate())
				.scheduledArrival(randomDate())
				.scheduledDeparture(randomDate())
				.estimatedDeparture(randomDate())
				.checkInStart(randomDate())
				.checkInEnd(randomDate())
				.arrivalAirport(arrivalAirports.get(random.nextInt(arrivalAirports.size())))
				.flightStatus("")
				.operatingAirline(airline)
				.iataCode(airline.substring(0, 3).toUpperCase())
				.departureAirport(departureAirports.get(random.nextInt(departureAirports.size())))
				.departureTerminal("A1")
				.aircraftModel(Flight.AircraftModel.values()[random.nextInt(3)])
				.build();
	}
}
