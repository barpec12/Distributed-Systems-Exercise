import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import interfaces.IFlightClient;
import interfaces.IFlightServer;
import model.Flight;

public class FlightServer implements IFlightServer {

	private static Logger logger = Logger.getLogger(FlightServer.class.getName());
	private HashMap<String, IFlightClient> loggedClients = new HashMap<>();
	private List<Flight> flights = new ArrayList<>();

	protected FlightServer() throws RemoteException {
		super();

		ZoneId zoneId = ZoneId.systemDefault();
		var departureTime = ZonedDateTime.of(LocalDateTime.of(2022, 12, 24, 15, 30), zoneId);
		var arrivalTime = ZonedDateTime.of(LocalDateTime.of(2022, 12, 24, 17, 30), zoneId);
		var flightBuilder = Flight.builder();
		flightBuilder.arrivalAirport("Frankfurt am Main").scheduledDeparture(arrivalTime).departureAirport("Warsaw")
				.scheduledArrival(departureTime).iataCode("LOT").operatingAirline("Lot").flightNumber("123").departureTerminal("A1");
		Flight f1 = flightBuilder.build();
		flightBuilder.operatingAirline("Lufthansa").iataCode("LTH").flightNumber("523");
		Flight f2 = flightBuilder.build();

		flights.addAll(Arrays.asList(f1, f2));
	}

	@Override
	public void login(String clientName, IFlightClient client) throws RemoteException {
		logger.log(Level.INFO, "New client logged in: " + clientName);
		loggedClients.put(clientName, client);
		client.receiveListOfFlights(flights);
	}

	@Override
	public void logout(String clientName) {
		loggedClients.remove(clientName);
		logger.log(Level.INFO, "Client logged out: " + clientName);
	}

	@Override
	public void updateFlight(String clientName, Flight flight) {
		flights.stream()
				.filter(element -> element.getFlightNumber().equals(flight.getFlightNumber()))
				.findAny().ifPresent(flights::remove);
		flights.add(flight);
		informAllClients(flight, false);
		logger.log(Level.INFO, "Update flight: " + flight.toString());
	}

	@Override
	public void deleteFlight(String clientName, Flight flight) {
		flights.remove(flight);
		informAllClients(flight, true);
		logger.log(Level.INFO, "Delete flight: " + flight.toString());
	}

	private void informAllClients(Flight flight, boolean deleted) {
		loggedClients.values().forEach(client -> {
			try {
				client.receiveUpdatedFlight(flight, deleted);
			} catch (RemoteException e) {
				throw new RuntimeException(e);
			}
		});
	}

	public static void main(String[] args) {
		try {
			var server = new FlightServer();
			var stub = (IFlightServer) UnicastRemoteObject.exportObject(server, 0);
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.rebind("TK1", stub);

			logger.info("Server is ready");
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Server exception", ex);
		}
	}

}
