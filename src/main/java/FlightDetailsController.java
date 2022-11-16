import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Flight;

import java.net.URL;
import java.rmi.RemoteException;
import java.time.*;
import java.util.ResourceBundle;

import static java.util.Objects.isNull;

public class FlightDetailsController implements Initializable {

    @FXML
    private TextField iataCode, flightNumber, operatingAirline, departureAirport, arrivalAirport, departureTerminal;

    @FXML
    private TextField scheduledDepartureHour, scheduledDepartureMinute;

    @FXML
    private DatePicker scheduledDepartureDate;

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
        flightNumber.setText(flight.getFlightNumber());
        operatingAirline.setText(flight.getOperatingAirline());
        departureAirport.setText(flight.getDepartureAirport());
        arrivalAirport.setText(flight.getArrivalAirport());
        departureTerminal.setText(flight.getDepartureTerminal());
        scheduledDepartureDate.setValue(flight.getScheduledDeparture().toLocalDate());
        scheduledDepartureMinute.setText(String.valueOf(flight.getScheduledDeparture().getMinute()));
        scheduledDepartureHour.setText(String.valueOf(flight.getScheduledDeparture().getHour()));
    }

    private void saveFlight() throws RemoteException {
        TableView<Flight> flightTableView =(TableView<Flight>)mainStage.getScene().lookup("#flightTable");
        if(isNull(flight)) {
            flight = new Flight();
            flightTableView.getItems().add(flight);
        }
        //start here
        //don't save empty data
        //requirements about some fields
        //2011-03-11T12:34+01:00[Europe/Belgrade]
        try {
            flight.setIataCode(iataCode.getText());
            flight.setFlightNumber(flightNumber.getText());
            flight.setOperatingAirline(operatingAirline.getText());
            flight.setDepartureAirport(departureAirport.getText());
            flight.setArrivalAirport(arrivalAirport.getText());
            flight.setDepartureTerminal(departureTerminal.getText());
            LocalDateTime localDateTime = LocalDateTime.of(scheduledDepartureDate.getValue(),
                    LocalTime.of(Integer.parseInt(scheduledDepartureHour.getText()),
                            Integer.parseInt(scheduledDepartureMinute.getText())));
            flight.setScheduledDeparture(ZonedDateTime.of(localDateTime, ZoneId.systemDefault()));
        } catch (NumberFormatException numberFormatException) {
            clearDateFieldProneToErrors();
            return;
        } catch (DateTimeException dateTimeException) {
            clearDateFieldProneToErrors();
            return;
        }
        stage.close();
        FlightClient.getIFlightServer().updateFlight(FlightClientApplication.getFlightClient().getClientName(), flight);
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
