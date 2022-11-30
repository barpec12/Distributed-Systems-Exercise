package pl.barpec12.tk1.flightclient;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
        seats = Arrays.asList(FlightClient.getFlightClient().getReservationBooking().getFreeSeats("43223"));
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

        setFlights(Arrays.asList(FlightClient.getFlightClient().getReservationBooking().getFreeSeats("43223")));

//        table_seats.getSelectionModel().selectedItemProperty().addListener(event ->{
//            try{
//
//
//            }catch(IOException e){
//                e.printStackTrace();
//            }
//        });
    }


    public void setFlights(List<Seat> seats) {
        table_seats.setItems(FXCollections.observableList(seats));
        table_seats.refresh();
    }

    public void reservation_process(){

    }


}
