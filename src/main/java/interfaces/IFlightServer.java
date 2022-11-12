package interfaces;

import model.Flight;

public interface IFlightServer {

	void login(String clientName, IFlightClient client);

	void logout(String clientName);

	void updateFlight(String clientName, Flight flight);
	
	void deleteFlight(String clientName, Flight flight);
}
