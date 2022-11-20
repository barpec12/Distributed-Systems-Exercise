import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import lombok.Getter;
import model.Flight;

public class FlightClient {
	private static Logger logger = Logger.getLogger(FlightClient.class.getName());
	@Getter
	private String clientId;
	@Getter
	private List<Flight> flightList = new ArrayList<>();

	public FlightClient(String clientId) {
		this.clientId = clientId;
	}

	private void updateTable() {
		logger.info("Updating table...");
		FlightsTableController.getFlightsTableController().setFlights(flightList);
	}
}
