package pl.barpec12.tk1.flightclient;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pl.barpec12.tk1.flightclient.model.Flight;
import pl.barpec12.tk1.flightclient.model.Seat;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {

    @FXML
    List<Seat> seats;

    @FXML
    private TableView<Seat> table_seats;
    public ReservationController(){}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table_seats.getColumns().clear();

        TableColumn<Seat, String> column1 = new TableColumn<>("row");
        column1.setCellValueFactory(new PropertyValueFactory<>("row"));

        TableColumn<Seat, String> column2 = new TableColumn<>("letter");
        column2.setCellValueFactory(new PropertyValueFactory<>("letter"));

        TableColumn<Seat, String> column3 = new TableColumn<>("emergency exit");
        column3.setCellValueFactory(new PropertyValueFactory<>("emergencySeat"));

        TableColumn<Seat, String> column4 = new TableColumn<>("Seat class");
        column4.setCellValueFactory(new PropertyValueFactory<>("seatClass"));

        TableColumn<Seat, String> column5 = new TableColumn<>("Price (Euro)");
        column5.setCellValueFactory(new PropertyValueFactory<>("price"));

        table_seats.getColumns().addAll(column1, column2, column3, column4, column5);


        table_seats.setRowFactory(tv -> {
            TableRow<Seat> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())) {
                    Seat seat_row = row.getItem();
                    reservation_confiramtion_process(seat_row);
                }
            });
            return row;
        });
    }

    public void fillWithData(Flight flight) {
        seats = Arrays.asList(FlightClient.getFlightClient().getReservationBooking().getFreeSeats(flight.getFlightNumber()));
        table_seats.setItems(FXCollections.observableList(seats));
        table_seats.refresh();
    }

    public void reservation_confiramtion_process(Seat seat){
        try {
            Stage stage = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("reservationConfirmation.fxml"));
            Parent root = fxmlLoader.load();

            ReservationConfirmation reservationConfirmation = fxmlLoader.getController();
            reservationConfirmation.fill_data(seat);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getClassLoader().getResource("styles.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }




}
