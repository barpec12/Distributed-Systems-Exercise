package pl.barpec12.tk1.flightserver;

import jakarta.xml.ws.Endpoint;
import lombok.Getter;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import pl.barpec12.tk1.flightserver.model.Flight;
import pl.barpec12.tk1.flightserver.model.Reservation;
import pl.barpec12.tk1.flightserver.model.Seat;
import pl.barpec12.tk1.flightserver.rest.ReservationBookingController;
import pl.barpec12.tk1.flightserver.soap.ReservationBookingImpl;

import java.net.URI;
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
	protected FlightServer() {
		var f = prepareBoeing();
		addFlight(f);
		var s = f.getSeats().stream().filter(seat -> seat.getLetter() == 'A' && seat.getRow() == 1).findAny().get();
		Reservation reservation = Reservation.builder().flightNumber(f.getFlightNumber()).meal(Reservation.Meal.STANDARD).seat(s).build();

		addReservation(reservation);
	}

	public static void main(String[] args) {
		flightServer = new FlightServer();
		Endpoint.publish("http://localhost:8090/ws/reservationBooking", new ReservationBookingImpl(flightServer));

		ResourceConfig resourceConfig = new ResourceConfig(ReservationBookingController.class);
		JdkHttpServerFactory.createHttpServer(URI.create("http://localhost:8080/"), resourceConfig, true);
	}

	public void addReservation(Reservation reservation) {
		reservations.add(reservation);
	}

	public void addFlight(Flight flight) {
		flights.add(flight);
	}

	private char letter(int number) {
		return (char) (number + 64);
	}

	private Flight prepareBoeing() {
		var flight = Flight.builder().flightNumber("43223").build();
		Set<Seat> seats = new HashSet<>();
		Seat.SeatBuilder seatBuilder = Seat.builder();
		seatBuilder.seatClass(Seat.SeatClass.FIRST);
		for(int i = 1; i<6; i++) {
			for(int j = 1; j<5; j++) {
				seats.add(seatBuilder.row(i).letter(letter(j)).build());
			}
		}
		seatBuilder.seatClass(Seat.SeatClass.ECONOMY_PLUS);
		for(int i = 8; i<12; i++) {
			for(int j = 1; j<7; j++) {
				seats.add(seatBuilder.row(i).letter(letter(j)).build());
			}
		}

		seatBuilder.row(7);
		seats.add(seatBuilder.letter('D').build());
		seats.add(seatBuilder.letter('E').build());
		seats.add(seatBuilder.letter('F').build());
		//
		seatBuilder.row(12);
		seats.add(seatBuilder.letter('A').build());
		seats.add(seatBuilder.letter('B').build());
		seats.add(seatBuilder.letter('C').build());
		seatBuilder.seatClass(Seat.SeatClass.ECONOMY);
		seats.add(seatBuilder.letter('D').build());
		seats.add(seatBuilder.letter('E').build());
		seats.add(seatBuilder.letter('F').build());
		//
		for(int i = 14; i<16; i++) {
			seatBuilder.row(i);
			for(int j = 1; j<7; j++) {
				seats.add(seatBuilder.letter(letter(j)).build());
			}
		}
		seatBuilder.seatClass(Seat.SeatClass.ECONOMY_PLUS);
		seatBuilder.isEmergencySeat(true);
		for(int i = 20; i<22; i++) {
			seatBuilder.row(i);
			for(int j = 1; j<7; j++) {
				seats.add(seatBuilder.letter(letter(j)).build());
			}
		}
		seatBuilder.isEmergencySeat(false);
		seatBuilder.seatClass(Seat.SeatClass.ECONOMY);
		for(int i = 22; i<40; i++) {
			seatBuilder.row(i);
			for(int j = 1; j<7; j++) {
				seats.add(seatBuilder.letter(letter(j)).build());
			}
		}
		flight.setSeats(seats);
		return flight;
	}

}
