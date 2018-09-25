package ml.echelon133.metronome;

import javafx.beans.property.DoublePropertyBase;
import ml.echelon133.metronome.event.ClickEvent;
import ml.echelon133.metronome.listener.IClickListener;

public class MeasureProgress extends DoublePropertyBase implements IClickListener {

    @Override
    public Object getBean() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void newClickEvent(ClickEvent clickEvent) {
        Integer currentClickNumber = clickEvent.getClickNumber();
        Integer accentInterval = clickEvent.getAccentInterval();
        // todo: fix this cryptic code, make it easier to understand
        set(((currentClickNumber - 1) % accentInterval + 1) / accentInterval.doubleValue());
    }
}
