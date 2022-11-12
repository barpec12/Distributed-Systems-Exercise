import java.util.logging.Level;
import java.util.logging.Logger;

import interfaces.IFlightClient;
import interfaces.IFlightServer;
import model.Flight;

public class FlightServer implements IFlightServer {

	private static Logger logger = Logger.getLogger(FlightServer.class.getName());


	protected FlightServer() {
		super();

		// initialize with some flights
		Flight flight1 = new Flight();
		

		Flight flight2 = new Flight();
		
		// ...
	}

	@Override
	public void login(String clientName, IFlightClient client) {
		logger.log(Level.INFO, "New client logged in: " + clientName);
	}

	@Override
	public void logout(String clientName) {
		logger.log(Level.INFO, "Client logged out: " + clientName);
	}

	@Override
	public void updateFlight(String clientName, Flight flight) {
		logger.log(Level.INFO, "Update flight: " + flight.toString());
	}

	@Override
	public void deleteFlight(String clientName, Flight flight) {
		logger.log(Level.INFO, "Delete flight: " + flight.toString());
	}

	private void informAllClients(Flight flight, boolean deleted) {
		
	}

	public static void main(String[] args) {
		try {
			// generate local registry

			// generate game server

			logger.info("Server is ready");
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Server exception", ex);
		}
	}

}
