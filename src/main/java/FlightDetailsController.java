import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Flight;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class FlightDetailsController implements Initializable {

    @FXML
    private TableView flightTable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
