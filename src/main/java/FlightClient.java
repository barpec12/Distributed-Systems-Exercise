import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import interfaces.IFlightClient;
import interfaces.IFlightServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Flight;

public class FlightClient extends Application implements IFlightClient {

	private static Logger logger = Logger.getLogger(FlightServer.class.getName());

	// ui

	// global state

//	public FlightClient(String clientName) {
//
//	}

	@Override
	public void receiveListOfFlights(List<Flight> flights) {
		logger.log(Level.INFO, "List of flights received: " + flights.size());
	}

	@Override
	public void receiveUpdatedFlight(Flight flight, boolean deleted) {
		logger.log(Level.INFO, "Flight updated: " + flight.toString());
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("flightsTable.fxml"));

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

		stage.setTitle("TK Airport Arrivals / Departures");
		stage.setScene(scene);
		stage.show();
	}

	public void startup() {
//		launch();
	}



	public static void main(String[] args) {
		launch();
//		FlightClient client = new FlightClient(UUID.randomUUID().toString());
//		client.startup();
	}

}
