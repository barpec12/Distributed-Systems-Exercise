import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.Getter;
import model.Flight;

public class FlightsTableController implements Initializable {

    @Getter
    private static FlightsTableController flightsTableController;
    @FXML
    private TableView<Flight> flightTable;
    @FXML
    private Button newButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    private Stage mainStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn<Flight, String> column1 = new TableColumn<>("Operating Airline");
        column1.setCellValueFactory(new PropertyValueFactory<>("operatingAirline"));

        TableColumn<Flight, String> column2 = new TableColumn<>("IATA Code");
        column2.setCellValueFactory(new PropertyValueFactory<>("iataCode"));

        TableColumn<Flight, String> column3 = new TableColumn<>("Flight Number");
        column3.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));

        flightTable.getColumns().addAll(column1, column2, column3);

        flightsTableController = this;

        try {
            FlightClientApplication.getFlightClient().startup();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        newButton.setOnAction(event -> {
            try {
                openFlightDetails(Optional.empty());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        editButton.setOnAction(event -> {
            Optional<Flight> flightOptional = Optional.ofNullable(flightTable.getSelectionModel().getSelectedItem());
            if(flightOptional.isEmpty()) return;
            try {
                openFlightDetails(flightOptional);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        deleteButton.setOnAction(event -> {
            Optional<Flight> flightOptional = Optional.ofNullable(flightTable.getSelectionModel().getSelectedItem());
            flightOptional.ifPresent(f -> {
                flightTable.getItems().remove(f);
                flightTable.refresh();
                try {
                    FlightClient.getIFlightServer().updateFlight(FlightClientApplication.getFlightClient().getClientName(), f);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });
        });

    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void setFlights(List<Flight> flights) {
        flightTable.setItems(FXCollections.observableList(flights));
        flightTable.refresh();
    }

    private void openFlightDetails(Optional<Flight> flightOptional) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("flightDetails.fxml"));
        Parent root = fxmlLoader.load();

        FlightDetailsController flightDetailsController = fxmlLoader.getController();
        flightDetailsController.setData(mainStage, stage);
        flightOptional.ifPresent(flight -> {
            flightDetailsController.fillWithData(flight);
        });

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        stage.setTitle("TK Airport Arrivals / Departures");
        stage.setScene(scene);
        stage.show();
    }
}
