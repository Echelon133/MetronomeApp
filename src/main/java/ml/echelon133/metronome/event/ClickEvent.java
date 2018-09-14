package ml.echelon133.metronome.event;

public final class ClickEvent {

    private final Integer clickNumber;
    private final Integer accentInterval;

    public ClickEvent(Integer clickNumber, Integer accentInterval) {
        this.clickNumber = clickNumber;
        this.accentInterval = accentInterval;
    }

    public Integer getClickNumber() {
        return clickNumber;
    }

    public Integer getAccentInterval() {
        return accentInterval;
    }
}
