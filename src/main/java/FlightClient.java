import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import interfaces.IFlightClient;
import interfaces.IFlightServer;
import lombok.Getter;
import model.Flight;

public class FlightClient implements IFlightClient {
	private static Logger logger = Logger.getLogger(FlightClient.class.getName());
	@Getter
	private static IFlightServer iFlightServer;
	@Getter
	private String clientName;
	@Getter
	private List<Flight> flightList = new ArrayList<>();

	public FlightClient(String clientName) {
		this.clientName = clientName;
	}

	@Override
	public void receiveListOfFlights(List<Flight> flights) {
		logger.log(Level.INFO, "List of flights received: " + flights.size());
		this.flightList = flights;
		System.out.println("TEST");
		updateTable();
	}

	@Override
	public void receiveUpdatedFlight(Flight flight, boolean deleted) {
		logger.log(Level.INFO, "Flight updated: " + flight.toString());
		if(deleted) {
			flightList.remove(flight);
		} else {
			flightList.stream()
					.filter(element -> element.getFlightNumber().equals(flight.getFlightNumber()))
					.findAny().ifPresent(flightList::remove);
			flightList.add(flight);
		}
		updateTable();
	}

	public void logout() throws RemoteException {
		iFlightServer.logout(clientName);
	}

	public void startup() throws RemoteException {
		try {
			Registry registry = LocateRegistry.getRegistry();
			iFlightServer = (IFlightServer) registry.lookup("TK1");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		UnicastRemoteObject.exportObject(this, 0);
		iFlightServer.login(clientName, this);
		System.out.println(clientName);
	}

	private void updateTable() {
		logger.info("Updating table...");
		FlightsTableController.getFlightsTableController().setFlights(flightList);
	}
}
