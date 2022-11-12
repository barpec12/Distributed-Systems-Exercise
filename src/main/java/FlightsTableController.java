import javafx.fxml.Initializable;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Flight;

public class FlightsTableController implements Initializable {

    @FXML
    private TableView flightTable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn<Flight, String> column1 = new TableColumn<>("Operating Airline");
        column1.setCellValueFactory(new PropertyValueFactory<>("operatingAirline"));

        TableColumn<Flight, String> column2 = new TableColumn<>("IATA Code");
        column2.setCellValueFactory(new PropertyValueFactory<>("iataCode"));
        flightTable.getColumns().addAll(column1, column2);

        ZoneId zoneId = ZoneId.systemDefault();
        var departureTime = ZonedDateTime.of(LocalDateTime.of(2022, 12, 24, 15, 30), zoneId);
        var arrivalTime = ZonedDateTime.of(LocalDateTime.of(2022, 12, 24, 17, 30), zoneId);
        var flightBuilder = Flight.builder();
        flightBuilder.arrival("Frankfurt am Main").estimatedDeparture(arrivalTime).departure("Warsaw")
                .scheduledDeparture(departureTime).iataCode("LOT").operatingAirline("Lot").trackingNumber("123").terminal("A1");
        Flight f1 = flightBuilder.build();
        flightBuilder.operatingAirline("Lufthansa").iataCode("LTH");
        Flight f2 = flightBuilder.build();
        flightTable.getItems().addAll(f1, f2);
        System.out.println(url.toString());
//        System.out.println(rb.toString());


    }
}
