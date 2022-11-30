package pl.barpec12.tk1.flightclient;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pl.barpec12.tk1.flightclient.model.Flight;
import pl.barpec12.tk1.flightclient.model.Reservation;
import pl.barpec12.tk1.flightclient.model.Seat;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReservationConfirmation implements Initializable {

    @FXML
    private Label labelSeat;

    @FXML
    private ComboBox foodBox;

    @FXML
    private Button confirmButton;

    @FXML
    Seat seat;

    private Flight flight;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        foodBox.getItems().add("Standard");
        foodBox.getItems().add("Vegetarian");
        foodBox.getItems().add("Vegan");



       confirmButton.setOnAction(action ->{
           try{
               //let's check if the user selected the proper value from the combobox
               if (foodBox.getSelectionModel().isEmpty()==true) System.out.println("wait");
               else {
                   confirmation_process();
                   Stage stage = (Stage) confirmButton.getScene().getWindow();
                   stage.close();

               }

           } catch (Exception e) {
               throw new RuntimeException(e);
           }
       });


    }

    public void fill_data(Seat seats, Flight flight){
        this.flight = flight;
        System.out.println(flight.getFlightNumber());
        seat = seats;
        labelSeat.setText(seat.getLetter()+Integer.toString(seat.getRow()));

    }

    private void confirmation_process(){

        System.out.println(foodBox.getValue().toString().toUpperCase());

        Reservation reservation1 = Reservation.builder()
                .seat(seat)
                .meal(Reservation.Meal.valueOf(foodBox.getValue().toString().toUpperCase()))
                .customerId(FlightClient.getFlightClient().getClientId())
                .flightNumber(flight.getFlightNumber())
                .build();

        FlightClient.getFlightClient().getReservationBooking().reserveFlight(flight.getFlightNumber(),reservation1);
    }
}
