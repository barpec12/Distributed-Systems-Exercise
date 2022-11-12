import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Flight;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import static java.util.Objects.isNull;

public class FlightDetailsController implements Initializable {

    @FXML
    private TextField iataCode;
    @FXML
    private TextField flightNumber;
    @FXML
    private TextField operatingAirline;


    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;

    private Flight flight;
    private Stage stage;
    private Stage mainStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cancelButton.setOnAction(event -> stage.close());
        saveButton.setOnAction(event -> {
            try {
                saveFlight();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void setData(Stage mainStage, Stage stage) {
        this.stage = stage;
        this.mainStage = mainStage;
    }

    public void fillWithData(Flight flight) {
        this.flight = flight;
        iataCode.setText(flight.getIataCode());
        flightNumber.setText(flight.getFlightNumber());
        operatingAirline.setText(flight.getOperatingAirline());
    }

    private void saveFlight() throws RemoteException {
        TableView<Flight> flightTableView = (TableView<Flight>) mainStage.getScene().lookup("#flightTable");
        if(isNull(flight)) {
            flight = new Flight();
            flightTableView.getItems().add(flight);
        }
        flight.setIataCode(iataCode.getText());
        flight.setFlightNumber(flightNumber.getText());
        flight.setOperatingAirline(operatingAirline.getText());
        stage.close();
        FlightClient.getIFlightServer().updateFlight(FlightClientApplication.getFlightClient().getClientName(), flight);
        flightTableView.refresh();
    }
}
