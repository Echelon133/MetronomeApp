package ml.echelon133;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import ml.echelon133.metronome.Metronome;
import ml.echelon133.metronome.TimeSignatureHolder;

public class MetronomeController {

    private Metronome metronome;

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

        bpmTextLabel.setText(Metronome.DEFAULT_BPM_VALUE.toString());
    }

    public void setMetronome(Metronome metronome) {
        this.metronome = metronome;
    }
}
