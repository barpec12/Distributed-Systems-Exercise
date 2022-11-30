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
    private TextField iataCode, flightNumber, operatingAirline, departureAirport, arrivalAirport, departureTerminal, aircraftModel, arrivalTerminal, departureGates, arrivalGates,
            checkInLocation, checkInCounter;

    @FXML
    private TextField scheduledDepartureHour, scheduledDepartureMinute, originDateHour, originDateMinute, scheduledArrivalHour, scheduledArrivalMinute,
            estimatedDepartureHour, estimatedDepartureMinute, estimatedArrivalHour, estimatedArrivalMinute, checkInStartHour, checkInStartMinute,
            checkInEndHour, checkInEndMinute;

    @FXML
    private DatePicker scheduledDepartureDate, originDateDate, scheduledArrivalDate, estimatedDepartureDate, estimatedArrivalDate, checkInStartDate, checkInEndDate;

    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;
    @FXML
    private ComboBox flightStatus, aircraftModelNameComboBox;

    private Flight flight;
    private Stage stage;
    private Stage mainStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initialize_choices_of_comboBox();
        initialize_choices_of_comboBox_of_model();
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
        aircraftModelNameComboBox.setValue(flight.getAircraftModel());
        flightNumber.setText(flight.getFlightNumber());
        operatingAirline.setText(flight.getOperatingAirline());
        departureAirport.setText(flight.getDepartureAirport());
        arrivalAirport.setText(flight.getArrivalAirport());
        departureTerminal.setText(flight.getDepartureTerminal());
        arrivalTerminal.setText(flight.getArrivalTerminal());
        departureGates.setText(flight.getDepartureListOfGates());
        arrivalGates.setText(flight.getArrivalListOfGates());
        checkInLocation.setText(flight.getCheckInLocation());
        checkInCounter.setText(flight.getCheckInCounter());
        flightStatus.setValue(flight.getFlightStatus());

        scheduledDepartureDate.setValue(flight.getScheduledDeparture().toLocalDate());
        scheduledDepartureMinute.setText(String.valueOf(flight.getScheduledDeparture().getMinute()));
        scheduledDepartureHour.setText(String.valueOf(flight.getScheduledDeparture().getHour()));

        originDateDate.setValue(flight.getOriginDate().toLocalDate());
        originDateHour.setText(String.valueOf(flight.getOriginDate().getHour()));
        originDateMinute.setText(String.valueOf(flight.getOriginDate().getMinute()));

        scheduledArrivalDate.setValue(flight.getScheduledArrival().toLocalDate());
        scheduledArrivalHour.setText(String.valueOf(flight.getScheduledArrival().getHour()));
        scheduledArrivalMinute.setText(String.valueOf(flight.getScheduledArrival().getMinute()));

        estimatedDepartureDate.setValue(flight.getEstimatedDeparture().toLocalDate());
        estimatedDepartureHour.setText(String.valueOf(flight.getEstimatedDeparture().getHour()));
        estimatedDepartureMinute.setText(String.valueOf(flight.getEstimatedDeparture().getMinute()));

        estimatedArrivalDate.setValue(flight.getEstimatedArrival().toLocalDate());
        estimatedArrivalHour.setText(String.valueOf(flight.getEstimatedArrival().getHour()));
        estimatedArrivalMinute.setText(String.valueOf(flight.getScheduledArrival().getMinute()));

        checkInStartDate.setValue(flight.getCheckInStart().toLocalDate());
        checkInStartHour.setText(String.valueOf(flight.getCheckInStart().getHour()));
        checkInStartMinute.setText(String.valueOf(flight.getCheckInStart().getMinute()));

        checkInEndDate.setValue(flight.getCheckInEnd().toLocalDate());
        checkInEndHour.setText(String.valueOf(flight.getCheckInEnd().getHour()));
        checkInEndMinute.setText(String.valueOf(flight.getCheckInEnd().getMinute()));

    }

    private void saveFlight() throws RemoteException {
        TableView<Flight> flightTableView = (TableView<Flight>)mainStage.getScene().lookup("#flightTable");
        var flightClient = FlightClient.getFlightClient();
        if(isNull(flight)) flight = new Flight();

        try {
            flight.setIataCode(iataCode.getText());
            flight.setAircraftModel(Flight.AircraftModel.fromName(aircraftModelNameComboBox.getValue().toString()));
            flight.setFlightNumber(flightNumber.getText());
            flight.setOperatingAirline(operatingAirline.getText());
            flight.setDepartureAirport(departureAirport.getText());
            flight.setArrivalAirport(arrivalAirport.getText());
            flight.setDepartureTerminal(departureTerminal.getText());
            flight.setArrivalTerminal(arrivalTerminal.getText());
            flight.setDepartureListOfGates(departureGates.getText());
            flight.setArrivalListOfGates(arrivalGates.getText());
            flight.setCheckInLocation(checkInLocation.getText());
            flight.setCheckInCounter(checkInCounter.getText());
            flight.setFlightStatus(flightStatus.getValue().toString());


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

            localDateTime = LocalDateTime.of(estimatedDepartureDate.getValue(),
                    LocalTime.of(Integer.parseInt(estimatedDepartureHour.getText()),
                            Integer.parseInt(estimatedDepartureMinute.getText())));
            flight.setEstimatedDeparture(ZonedDateTime.of(localDateTime, ZoneId.systemDefault()));

            localDateTime = LocalDateTime.of(estimatedArrivalDate.getValue(),
                    LocalTime.of(Integer.parseInt(estimatedArrivalHour.getText()),
                            Integer.parseInt(estimatedArrivalMinute.getText())));
            flight.setEstimatedArrival(ZonedDateTime.of(localDateTime, ZoneId.systemDefault()));

            localDateTime = LocalDateTime.of(checkInStartDate.getValue(),
                    LocalTime.of(Integer.parseInt(checkInStartHour.getText()),
                            Integer.parseInt(checkInStartMinute.getText())));
            flight.setCheckInStart(ZonedDateTime.of(localDateTime, ZoneId.systemDefault()));

            localDateTime = LocalDateTime.of(checkInEndDate.getValue(),
                    LocalTime.of(Integer.parseInt(checkInEndHour.getText()),
                            Integer.parseInt(checkInEndMinute.getText())));
            flight.setCheckInEnd(ZonedDateTime.of(localDateTime, ZoneId.systemDefault()));

        } catch (NumberFormatException | DateTimeException numberFormatException) {
            clearDateFieldProneToErrors();
            return;
        }
        if(Objects.isNull(flightNumber.getText())|| flightNumber.getText().length() < 1) {
            clearDateFieldProneToErrors();
            return;
        }
        flightClient.getReservationBooking().addFlight(flight);
        flightClient.refreshFlights();
        stage.close();
    }

    private void clearDateFieldProneToErrors() {
        scheduledDepartureHour.setText("");
        scheduledDepartureMinute.setText("");
    }
    private void initialize_choices_of_comboBox(){
        flightStatus.getItems().add("'B' = Arrival by bus at Concourse B");
        flightStatus.getItems().add("'D' = Diverted");
        flightStatus.getItems().add("'I' = Undefined late arrival or departure");
        flightStatus.getItems().add("'L' = Aborted departure");
        flightStatus.getItems().add("'M' = Flight delayed until tomorrow");
        flightStatus.getItems().add("'Y' = Return to stand");
        flightStatus.getItems().add("'Z' = Returned to apron");

    }

    private void initialize_choices_of_comboBox_of_model(){
        aircraftModelNameComboBox.getItems().add("Boeing 737-900");
        aircraftModelNameComboBox.getItems().add("Airbus 319");
        aircraftModelNameComboBox.getItems().add("Embraer E170");
    }
}
