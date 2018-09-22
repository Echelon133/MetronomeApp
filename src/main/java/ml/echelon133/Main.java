package ml.echelon133;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/ml/echelon133/fxml/metronome.fxml"));
        Media strongBeat = new Media(getClass().getResource("/ml/echelon133/sounds/strong_beat.mp3").toExternalForm());
        Media weakBeat = new Media(getClass().getResource("/ml/echelon133/sounds/weak_beat.mp3").toExternalForm());

        primaryStage.setTitle("MetronomeApp");
        primaryStage.setScene(new Scene(root, 360, 225));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
