package pl.barpec12.tk1.flightclient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.xml.ws.Service;
import lombok.Getter;
import org.glassfish.jersey.client.ClientConfig;
import pl.barpec12.tk1.flightclient.model.Flight;
import pl.barpec12.tk1.flightclient.soap.ReservationBooking;

import javax.xml.namespace.QName;

public class FlightClient {
	private static Logger logger = Logger.getLogger(FlightClient.class.getName());
	@Getter
	private String clientId;
	@Getter
	private List<Flight> flightList = new ArrayList<>();

	public FlightClient(String clientId) {
		this.clientId = clientId;
		try {
			URL url = new URL("http://localhost:8090/ws/reservationBooking?wsdl");
			QName qname = new QName("http://soap.flightserver.tk1.barpec12.pl/", "ReservationBookingImplService");
			QName port = new QName("http://soap.flightserver.tk1.barpec12.pl/", "ReservationBookingImplPort");

			Service service = Service.create(url, qname);
			ReservationBooking reservationBooking = service.getPort(port, ReservationBooking.class);
			System.out.println(Arrays.asList(reservationBooking.getFlights()));

			Client client = ClientBuilder.newClient();
			WebTarget reservation = client.target("http://localhost:8080/reservation");
			WebTarget flightsList = reservation.path("/flights");
			List<Flight> flights = flightsList.request(MediaType.APPLICATION_JSON).get(List.class);
			System.out.println(flights);

		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	private void updateTable() {
		logger.info("Updating table...");
		FlightsTableController.getFlightsTableController().setFlights(flightList);
	}
}