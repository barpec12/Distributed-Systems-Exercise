package interfaces;

import java.util.List;

import model.Flight;

public interface IFlightClient {
	
	void receiveListOfFlights(List<Flight> flights);
	
	void receiveUpdatedFlight(Flight flight, boolean deleted);

}
