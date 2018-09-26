package ml.echelon133.metronome;

import ml.echelon133.metronome.event.ClickEvent;
import ml.echelon133.metronome.listener.IClickListener;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStream;

public class WavClickPlayer implements IClickListener {

    private Clip strongBeat;
    private Clip weakBeat;

    public WavClickPlayer(InputStream strongBeatStream, InputStream weakBeatStream) {
        try {
            strongBeat = AudioSystem.getClip();
            weakBeat = AudioSystem.getClip();
            strongBeat.open(AudioSystem.getAudioInputStream(strongBeatStream));
            weakBeat.open(AudioSystem.getAudioInputStream(weakBeatStream));
        } catch (LineUnavailableException | UnsupportedAudioFileException| IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void newClickEvent(ClickEvent clickEvent) {
        Integer currentClickNumber = clickEvent.getClickNumber();
        Integer beatsInMeasure = clickEvent.getAccentInterval();

        // Play strong beat only on the first click of the measure
        if (currentClickNumber % beatsInMeasure == 1) {
            strongBeat.start();
            strongBeat.setFramePosition(0);
        } else {
            weakBeat.start();
            weakBeat.setFramePosition(0);
        }
    }
}
