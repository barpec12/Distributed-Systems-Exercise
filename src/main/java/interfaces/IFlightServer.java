package interfaces;

import model.Flight;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IFlightServer extends Remote {

	void login(String clientName, IFlightClient client) throws RemoteException;

	void logout(String clientName) throws RemoteException;

	void updateFlight(String clientName, Flight flight) throws RemoteException;
	
	void deleteFlight(String clientName, Flight flight) throws RemoteException;
}
