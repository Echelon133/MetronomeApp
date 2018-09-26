package ml.echelon133;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ml.echelon133.metronome.*;
import ml.echelon133.metronome.listener.IClickListener;

import java.io.InputStream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ml/echelon133/fxml/metronome.fxml"));
        Parent root = loader.load();
        InputStream strongBeatStream = getClass().getResource("/ml/echelon133/sounds/strong_beat.wav").openStream();
        InputStream weakBeatStream = getClass().getResource("/ml/echelon133/sounds/weak_beat.wav").openStream();

        IClickListener clickPlayer = new WavClickPlayer(strongBeatStream, weakBeatStream);
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
