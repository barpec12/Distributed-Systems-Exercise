package pl.barpec12.tk1.flightclient;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.barpec12.tk1.flightclient.model.Flight;

import java.net.URL;
import java.rmi.RemoteException;
import java.time.*;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.util.Objects.isNull;

public class FlightDetailsController implements Initializable {

    @FXML
    private TextField iataCode, flightNumber, operatingAirline, departureAirport, arrivalAirport, departureTerminal, aircraftModel, arrivalTerminal, departureGates, arrivalGates;

    @FXML
    private TextField scheduledDepartureHour, scheduledDepartureMinute, originDateHour, originDateMinute, scheduledArrivalHour, scheduledArrivalMinute;

    @FXML
    private DatePicker scheduledDepartureDate, originDateDate, scheduledArrivalDate;

    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;
    @FXML
    private ComboBox comboBox;

    private Flight flight;
    private Stage stage;
    private Stage mainStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initialize_choices_of_comboBox();
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
        aircraftModel.setText(flight.getAircraftModel());
        flightNumber.setText(flight.getFlightNumber());
        operatingAirline.setText(flight.getOperatingAirline());
        departureAirport.setText(flight.getDepartureAirport());
        arrivalAirport.setText(flight.getArrivalAirport());
        departureTerminal.setText(flight.getDepartureTerminal());
        arrivalTerminal.setText(flight.getArrivalTerminal());
        departureGates.setText(flight.getDepartureListOfGates());
        arrivalGates.setText(flight.getArrivalListOfGates());

        scheduledDepartureDate.setValue(flight.getScheduledDeparture().toLocalDate());
        scheduledDepartureMinute.setText(String.valueOf(flight.getScheduledDeparture().getMinute()));
        scheduledDepartureHour.setText(String.valueOf(flight.getScheduledDeparture().getHour()));

        originDateDate.setValue(flight.getOriginDate().toLocalDate());
        originDateHour.setText(String.valueOf(flight.getOriginDate().getHour()));
        originDateMinute.setText(String.valueOf(flight.getOriginDate().getMinute()));

        scheduledArrivalDate.setValue(flight.getScheduledArrival().toLocalDate());
        scheduledArrivalHour.setText(String.valueOf(flight.getScheduledArrival().getHour()));
        scheduledArrivalMinute.setText(String.valueOf(flight.getScheduledArrival().getMinute()));
    }

    private void saveFlight() throws RemoteException {
        TableView<Flight> flightTableView = (TableView<Flight>)mainStage.getScene().lookup("#flightTable");
        if(isNull(flight)) {
            flight = new Flight();
            flightTableView.getItems().add(flight);
        }

        try {
            flight.setIataCode(iataCode.getText());
            flight.setAircraftModel(aircraftModel.getText());
            flight.setFlightNumber(flightNumber.getText());
            flight.setOperatingAirline(operatingAirline.getText());
            flight.setDepartureAirport(departureAirport.getText());
            flight.setArrivalAirport(arrivalAirport.getText());
            flight.setDepartureTerminal(departureTerminal.getText());
            flight.setArrivalTerminal(arrivalTerminal.getText());
            flight.setDepartureListOfGates(departureGates.getText());
            flight.setArrivalListOfGates(arrivalGates.getText());

            LocalDateTime localDateTime = LocalDateTime.of(scheduledDepartureDate.getValue(),
                    LocalTime.of(Integer.parseInt(scheduledDepartureHour.getText()),
                            Integer.parseInt(scheduledDepartureMinute.getText())));
            flight.setScheduledDeparture(ZonedDateTime.of(localDateTime, ZoneId.systemDefault()));

            localDateTime = LocalDateTime.of(originDateDate.getValue(),
                    LocalTime.of(Integer.parseInt(originDateHour.getText()),
                            Integer.parseInt(originDateMinute.getText())));
            flight.setOriginDate(ZonedDateTime.of(localDateTime, ZoneId.systemDefault()));

            localDateTime = LocalDateTime.of(scheduledArrivalDate.getValue(),
                    LocalTime.of(Integer.parseInt(scheduledArrivalHour.getText()),
                            Integer.parseInt(scheduledArrivalMinute.getText())));
            flight.setScheduledArrival(ZonedDateTime.of(localDateTime, ZoneId.systemDefault()));

        } catch (NumberFormatException | DateTimeException numberFormatException) {
            clearDateFieldProneToErrors();
            return;
        }
        if(Objects.isNull(flightNumber.getText())|| flightNumber.getText().length() < 1) {
            clearDateFieldProneToErrors();
            return;
        }
        stage.close();
        flightTableView.refresh();
    }

    private void clearDateFieldProneToErrors() {
        scheduledDepartureHour.setText("");
        scheduledDepartureMinute.setText("");
    }
    private void initialize_choices_of_comboBox(){
        comboBox.getItems().add("'B' = Arrival by bus at Concourse B");
        comboBox.getItems().add("'D' = Diverted");
        comboBox.getItems().add("'I' = Undefined late arrival or departure");
        comboBox.getItems().add("'L' = Aborted departure");
        comboBox.getItems().add("'M' = Flight delayed until tomorrow");
        comboBox.getItems().add("'Y' = Return to stand");
        comboBox.getItems().add("'Z' = Returned to apron");

    }
}
