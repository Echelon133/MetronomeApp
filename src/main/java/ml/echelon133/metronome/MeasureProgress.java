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

        /*
            Set the double value to the value that shows the progress of current measure

            In 4/4 first click should show 25% of the measure, second click shows 50%, third 75%, fourth 100% and
            then the cycle repeats.

            In 5/4 first click shows 20%, second 40%, etc.

            Progress is a double value between 0.0 and 1.0, 1.0 being 100%
        */
        double currentProgress;

        // Metronome starts clicking from 1, but to calculate progress we have to start from 0
        int adjustedClickNumber = currentClickNumber - 1;

        // Ex. accentInterval is 4, we need a cycle of doubles (0.25, 0.5, 0.75, 1.0)
        // Ex. accentInterval is 3, we need a cycle of doubles (0.33, 0.66, 0.99)
        // ClickEvent click numbers do NOT repeat in a single metronome run, so we need to use modulo to get a cycle
        currentProgress = ((adjustedClickNumber % accentInterval) + 1) / accentInterval.doubleValue();

        // Update progress, to make all listeners aware of the change
        set(currentProgress);
    }
}
