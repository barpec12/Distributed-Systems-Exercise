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

import static pl.barpec12.tk1.flightserver.model.Flight.AircraftModel.*;

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
		var f = flightBuilder
				.flightNumber("43223")
				.originDate(randomDate())
				.estimatedArrival(randomDate())
				.scheduledArrival(randomDate())
				.scheduledDeparture(randomDate())
				.estimatedDeparture(randomDate())
				.checkInStart(randomDate())
				.checkInEnd(randomDate())
				.arrivalAirport("TK Airport")
				.flightStatus("")
				.operatingAirline("Lufthansa")
				.iataCode("LH")
				.departureAirport("Frankfurt")
				.departureTerminal("A1")
				.aircraftModel(Boeing_737_900)
				.build();
		addFlight(f);

		flightBuilder
				.flightNumber("333")
				.arrivalAirport("Darmstadt")
				.aircraftModel(Airbus_319)
				.scheduledDeparture(randomDate());
		f = flightBuilder.build();
		addFlight(f);

		flightBuilder
				.flightNumber("5456")
				.arrivalAirport("Warsaw")
				.aircraftModel(Embraer_E170)
				.scheduledDeparture(randomDate());
		f = flightBuilder.build();
		addFlight(f);

		var s = f.getSeats().stream().filter(seat -> seat.getLetter() == 'A' && seat.getRow() == 1).findAny().get();
		Reservation reservation = Reservation.builder().flightNumber(f.getFlightNumber()).meal(Reservation.Meal.STANDARD).seat(s).build();

		addReservation(reservation);
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



}
