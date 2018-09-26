package ml.echelon133.metronome;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class MetronomeController {

    private IMetronome metronome;

    @FXML private Button startButton;
    @FXML private Button stopButton;
    @FXML private Button decrementBy5Button;
    @FXML private Button decrementBy1Button;
    @FXML private Button incrementBy1Button;
    @FXML private Button incrementBy5Button;
    @FXML private ChoiceBox<String> timeSignatureChoiceBox;
    @FXML private ProgressBar beatProgressBar;
    @FXML private Label bpmTextLabel;

    @FXML
    public void initialize() {
        ObservableList<String> allTimeSignatures = FXCollections.observableList(
                TimeSignatureHolder.getAllSignatureNames()
        );

        timeSignatureChoiceBox.setItems(allTimeSignatures);
        timeSignatureChoiceBox.getSelectionModel().select("4/4");

        bpmTextLabel.setText(IMetronome.DEFAULT_BPM_VALUE.toString());

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stopButton.setDisable(false);
                startButton.setDisable(true);

                beatProgressBar.progressProperty().unbind();
                beatProgressBar.progressProperty().bind(metronome.getProgress());

                // disable increment/decrement buttons, so that bpm can be only changed when metronome is stopped
                incrementBy1Button.setDisable(true);
                incrementBy5Button.setDisable(true);
                decrementBy1Button.setDisable(true);
                decrementBy5Button.setDisable(true);

                String timeSignature = timeSignatureChoiceBox.getValue();
                metronome.setAccentInterval(TimeSignatureHolder.getNumberOfBeatsInSignature(timeSignature));

                // signal the thread that it should start firing click events
                metronome.setDone(false);
            }
        });

        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stopButton.setDisable(true);
                startButton.setDisable(false);

                // enable back increment/decrement buttons, so that bpm can be changed when metronome is stopped
                incrementBy1Button.setDisable(false);
                incrementBy5Button.setDisable(false);
                decrementBy1Button.setDisable(false);
                decrementBy5Button.setDisable(false);

                // make the thread await for another signal
                metronome.setDone(true);
                metronome.resetCounter();

                beatProgressBar.progressProperty().unbind();
                beatProgressBar.setProgress(0.0);
            }
        });

        incrementBy1Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                metronome.incrementBPMByOne();
                bpmTextLabel.setText(metronome.getBPM().toString());
            }
        });

        incrementBy5Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                metronome.incrementBPMByFive();
                bpmTextLabel.setText(metronome.getBPM().toString());
            }
        });

        decrementBy1Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                metronome.decrementBPMByOne();
                bpmTextLabel.setText(metronome.getBPM().toString());
            }
        });

        decrementBy5Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                metronome.decrementBPMByFive();
                bpmTextLabel.setText(metronome.getBPM().toString());
            }
        });
    }

    public void setMetronome(IMetronome metronome) {
        this.metronome = metronome;
    }
}
