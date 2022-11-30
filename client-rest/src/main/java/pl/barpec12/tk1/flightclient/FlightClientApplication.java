package pl.barpec12.tk1.flightclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

import java.util.UUID;

public class FlightClientApplication extends Application {

    @Getter
    private static FlightClient flightClient;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("flightsTable.fxml"));
        Parent root = fxmlLoader.load();

        FlightsTableController flightsTableController = fxmlLoader.getController();
        flightsTableController.setMainStage(stage);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("styles.css").toExternalForm());

        stage.setTitle("TK Airport Arrivals / Departures");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        flightClient = new FlightClient(UUID.randomUUID().toString());
        Application.launch();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
