package pl.barpec12.tk1.flightclient;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import lombok.Getter;
import pl.barpec12.tk1.flightclient.model.Flight;
import pl.barpec12.tk1.flightclient.rest.ReservationBooking;
import pl.barpec12.tk1.flightclient.rest.ReservationBookingImpl;

public class FlightClient {
	private static Logger logger = Logger.getLogger(FlightClient.class.getName());
	@Getter
	private String clientId;
	@Getter
	private static FlightClient flightClient;
	@Getter
	private List<Flight> flightList;
	@Getter
	private ReservationBooking reservationBooking;

	public FlightClient(String clientId) {
		this.clientId = clientId;
		flightClient = this;

		Client client = ClientBuilder.newClient();
		WebTarget reservation = client.target("http://localhost:8080/reservation");
		reservationBooking = new ReservationBookingImpl(reservation);

		downloadFlights();
	}

	private void downloadFlights() {
		flightList = Arrays.asList(reservationBooking.getFlights());
	}

	public void refreshFlights() {
		downloadFlights();
		updateTable();
	}
	public void updateTable() {
		logger.info("Updating table...");
		FlightsTableController.getFlightsTableController().setFlights(flightList);
	}

}
