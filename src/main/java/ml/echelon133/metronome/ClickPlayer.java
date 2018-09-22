package ml.echelon133.metronome;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import ml.echelon133.metronome.event.ClickEvent;
import ml.echelon133.metronome.listener.IClickListener;

public class ClickPlayer implements IClickListener {

    private MediaPlayer strongBeat;
    private MediaPlayer weakBeat;

    public ClickPlayer(Media strongBeat, Media weakBeat) {
        this.strongBeat = new MediaPlayer(strongBeat);
        this.weakBeat = new MediaPlayer(weakBeat);
    }

    @Override
    public void newClickEvent(ClickEvent clickEvent) {
        Integer currentClickNumber = clickEvent.getClickNumber();
        Integer beatsInMeasure = clickEvent.getAccentInterval();

        // Play strong beat only on the first click of the measure
        if (currentClickNumber % beatsInMeasure == 1) {
            strongBeat.play();
            strongBeat.seek(new Duration(0));
        } else {
            weakBeat.play();
            weakBeat.seek(new Duration(0));
        }
    }
}
