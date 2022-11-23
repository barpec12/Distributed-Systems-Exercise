package pl.barpec12.tk1.flightclient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import jakarta.xml.ws.Service;
import lombok.Getter;
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
			URL url = new URL("http://localhost:9999/ws/reservationBooking?wsdl");
			QName qname = new QName("http://soap.flightserver.tk1.barpec12.pl/", "ReservationBookingImplService");
			QName port = new QName("http://soap.flightserver.tk1.barpec12.pl/", "ReservationBookingImplPort");

			Service service = Service.create(url, qname);
			ReservationBooking reservationBooking = service.getPort(port, ReservationBooking.class);
			System.out.println(Arrays.asList(reservationBooking.getFlights()));
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	private void updateTable() {
		logger.info("Updating table...");
		FlightsTableController.getFlightsTableController().setFlights(flightList);
	}
}
