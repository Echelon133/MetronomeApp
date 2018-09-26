package ml.echelon133.metronome;

import javafx.beans.value.ObservableDoubleValue;

public interface IMetronome {
    Integer MIN_BPM_VALUE = 50;
    Integer MAX_BPM_VALUE = 240;
    Integer DEFAULT_BPM_VALUE = 80;
    Integer DEFAULT_ACCENT_INTERVAL = 4;

    void incrementBPMByOne();
    void decrementBPMByOne();
    void incrementBPMByFive();
    void decrementBPMByFive();
    long calculatePause();
    void setAccentInterval(Integer accentInterval);
    void setMeasureProgress(ObservableDoubleValue measureProgress);
    Integer getBPM();
    ObservableDoubleValue getProgress();
    void setDone(Boolean b);
    void resetCounter();
}
