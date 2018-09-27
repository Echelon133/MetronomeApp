package ml.echelon133.metronome;

import ml.echelon133.metronome.listener.TestClickListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
public class MetronomeThreadTest {

    private Metronome metronome;
    private Thread metronomeThread;
    private TestClickListener testClickListener;

    @BeforeEach
    public void beforeEach() {
        metronome = new Metronome();
        metronomeThread = new Thread(metronome);
        testClickListener = new TestClickListener();
        metronome.addClickListener(testClickListener);
    }

    @Test
    public void metronomeSendsValidNumberOfEventsAt60BPM() {
        // Bpm decreased from 80 to 60
        for (int i = 0; i < 4; i++) {
            metronome.decrementBPMByFive();
        }

        // Start the thread, then terminate it after fixed time and check whether the events are as expected
        metronome.setDone(false);
        metronomeThread.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        metronome.setDone(true);

        // Check whether there were 10 clicks in 10 seconds (because bpm is 60)
        assertEquals(10, testClickListener.getNumberOfClicks().intValue());
        // Check whether every event had the same accent interval (only one element in the set, by default of value 4);
        assertEquals(1, testClickListener.getAllAccentIntervals().size());
        assertTrue(testClickListener.getAllAccentIntervals().contains(4));
    }

    @Test
    public void metronomeSendsValidNumberOfEventsAt120BPM() {
        // Bpm increased from 80 to 120
        for (int i = 0; i < 8; i++) {
            metronome.incrementBPMByFive();
        }

        // Start the thread, then terminate it after fixed time and check whether the events are as expected
        metronome.setDone(false);
        metronomeThread.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        metronome.setDone(true);

        // Check whether there were 20 clicks in 10 seconds (because bpm is 120)
        assertEquals(20, testClickListener.getNumberOfClicks().intValue());
        // Check whether every event had the same accent interval (only one element in the set, by default of value 4);
        assertEquals(1, testClickListener.getAllAccentIntervals().size());
        assertTrue(testClickListener.getAllAccentIntervals().contains(4));
    }
}
