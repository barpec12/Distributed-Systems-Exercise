package pl.barpec12.tk1.flightclient;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
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
import pl.barpec12.tk1.flightclient.model.Flight;

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

    @FXML
    private Stage mainStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn<Flight, String> column1 = new TableColumn<>("Operating Airline");
        column1.setCellValueFactory(new PropertyValueFactory<>("operatingAirline"));

        TableColumn<Flight, String> column2 = new TableColumn<>("IATA Code");
        column2.setCellValueFactory(new PropertyValueFactory<>("iataCode"));

        TableColumn<Flight, String> column3 = new TableColumn<>("Flight Number");
        column3.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));

        TableColumn<Flight, String> column4 = new TableColumn<>("Departure");
        column4.setCellValueFactory(new PropertyValueFactory<>("departureAirport"));

        TableColumn<Flight, String> column5 = new TableColumn<>("Arrival");
        column5.setCellValueFactory(new PropertyValueFactory<>("arrivalAirport"));

        TableColumn<Flight, String> column6 = new TableColumn<>("Terminal");
        column6.setCellValueFactory(new PropertyValueFactory<>("departureTerminal"));

        TableColumn<Flight, String> column7 = new TableColumn<>("Scheduled Time");
        column7.setCellValueFactory(cellData -> {
            String localScheduledDate = cellData.getValue().getScheduledDeparture().toLocalDateTime()
                    .toString().replace("T", " ").split("\\.")[0];
            return new SimpleStringProperty(localScheduledDate);
        });

        TableColumn<Flight, String> column8 = new TableColumn<>("Aircraft Model");
        column8.setCellValueFactory(new PropertyValueFactory<>("aircraftModelNameComboBox"));

        flightTable.getColumns().addAll(column1, column2, column3, column4, column5, column6, column7, column8);

        setFlights(FlightClient.getFlightClient().getFlightList());

        flightsTableController = this;

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
//            editButton.setOnAction(event -> {
//            try {
//                openReservationService();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });

        deleteButton.setOnAction(event -> {
            Optional<Flight> flightOptional = Optional.ofNullable(flightTable.getSelectionModel().getSelectedItem());
            flightOptional.ifPresent(f -> {
                flightTable.getItems().remove(f);
                flightTable.refresh();
                //TODO remove flight
            });
        });

        flightTable.getSelectionModel().selectedItemProperty().addListener(event ->{
            try{
                openReservationService();
            }catch (IOException e){
                e.printStackTrace();
            }
                });

//        flightTable.setOnMouseClicked(event -> {
//            try{
//                openReservationService();
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        });

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("flightDetails.fxml"));
        Parent root = fxmlLoader.load();

        FlightDetailsController flightDetailsController = fxmlLoader.getController();
        flightDetailsController.setData(mainStage, stage);
        flightOptional.ifPresent(flight -> {
            flightDetailsController.fillWithData(flight);
        });

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("styles.css").toExternalForm());

        stage.setTitle("TK Airport Arrivals / Departures");
        stage.setScene(scene);
        stage.show();
    }

    private void openReservationService()throws IOException{
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("reservationService.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
