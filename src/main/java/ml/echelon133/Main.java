package ml.echelon133;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import ml.echelon133.metronome.ClickPlayer;
import ml.echelon133.metronome.MeasureProgress;
import ml.echelon133.metronome.Metronome;
import ml.echelon133.metronome.MetronomeController;
import ml.echelon133.metronome.listener.IClickListener;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ml/echelon133/fxml/metronome.fxml"));
        Parent root = loader.load();
        Media strongBeat = new Media(getClass().getResource("/ml/echelon133/sounds/strong_beat.mp3").toExternalForm());
        Media weakBeat = new Media(getClass().getResource("/ml/echelon133/sounds/weak_beat.mp3").toExternalForm());

        IClickListener clickPlayer = new ClickPlayer(strongBeat, weakBeat);
        IClickListener measureProgress = new MeasureProgress();

        Metronome metronome = new Metronome();
        // set done to true, otherwise metronome starts sending click events right after its thread start
        metronome.setDone(true);

        metronome.addClickListener(clickPlayer);

        // measureProgress is both a listener and a wrapper for ObservableDoubleValue
        // it has to listen to clicks, so that it can recalculate its value on every click
        // it also has to be passed to the metronome instance as a field, so that metronome controller can get a reference to it
        metronome.addClickListener(measureProgress);
        metronome.setMeasureProgress((MeasureProgress)measureProgress);

        Thread metronomeThread = new Thread(metronome);
        metronomeThread.setDaemon(true);
        metronomeThread.start();

        MetronomeController metronomeController = loader.getController();
        metronomeController.setMetronome(metronome);

        primaryStage.setTitle("MetronomeApp");
        primaryStage.setScene(new Scene(root, 360, 225));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
