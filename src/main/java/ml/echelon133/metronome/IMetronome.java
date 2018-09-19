package ml.echelon133.metronome;

public interface IMetronome {
    void incrementBPMByOne();
    void decrementBPMByOne();
    void incrementBPMByFive();
    void decrementBPMByFive();
    long calculatePause();
    void setAccentInterval(Integer accentInterval);
    Integer getBPM();
    void resetCounter();
}
