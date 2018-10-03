package ml.echelon133.metronome;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
public class MetronomeTest {

    private static IMetronome metronome;
    private static Random random;

    static {
        random = new Random();
    }

    @BeforeEach
    public void beforeEach() {
        metronome = new Metronome();
    }

    @Test
    public void checkDefaultBPMValue() {
        Integer bpmValue = metronome.getBPM();
        assertEquals(bpmValue.intValue(), 80);
    }

    @Test
    public void bpmModifiersWorkProperly() {
        int mirrorValue = 80;

        for (int i = 0; i < random.nextInt(10); i++) {
            metronome.incrementBPMByOne();
            mirrorValue++;
        }

        for (int i = 0; i < random.nextInt(10); i++) {
            metronome.incrementBPMByFive();
            mirrorValue += 5;
        }

        for (int i = 0; i < random.nextInt(10); i++) {
            metronome.decrementBPMByOne();
            mirrorValue--;
        }

        for (int i = 0; i < random.nextInt(10); i++) {
            metronome.decrementBPMByFive();
            mirrorValue -= 5;
        }

        assertEquals(mirrorValue, metronome.getBPM().intValue());
    }

    @Test
    public void pauseCalculationIsCorrectFor60BPM() {
        // 60 bpm -> 1000 ms between clicks (default bpm value is 80)
        for (int i = 0; i < 4; i++) {
            metronome.decrementBPMByFive();
        }
        Long pause = metronome.calculatePause();
        assertEquals(pause.doubleValue(), 1000.0, 1);
    }

    @Test
    public void pauseCalculationIsCorrectFor120BPM() {
        // 120 bpm -> 500 ms between clicks (default bpm value is 80)
        for (int i = 0; i < 8; i++) {
            metronome.incrementBPMByFive();
        }
        Long pause = metronome.calculatePause();
        assertEquals(pause.doubleValue(), 500.0, 1);
    }

    @Test
    public void pauseCalculationIsCorrectFor200BPM() {
        // 200 bpm -> 300 ms between clicks (default bpm value is 80)
        for (int i = 0; i < 24; i++) {
            metronome.incrementBPMByFive();
        }
        Long pause = metronome.calculatePause();
        assertEquals(pause.doubleValue(), 300.0, 1);
    }

    @Test
    public void decrementBPMByFiveCannotGoLowerThanLowerBoundary() {
        Integer bpmLowerBoundary = Metronome.MIN_BPM_VALUE;

        // try to decrement bpm by 1000
        for (int i = 0; i < 200; i++) {
            metronome.decrementBPMByFive();
        }

        assertEquals(bpmLowerBoundary.intValue(), metronome.getBPM().intValue());
    }

    @Test
    public void decrementBPMByOneCannotGoLowerThanLowerBoundary() {
        Integer bpmLowerBoundary = Metronome.MIN_BPM_VALUE;

        // try to decrement bpm by 200
        for (int i = 0; i < 200; i++) {
            metronome.decrementBPMByOne();
        }

        assertEquals(bpmLowerBoundary.intValue(), metronome.getBPM().intValue());
    }

    @Test
    public void incrementBPMByFiveCannotGoHigherThanUpperBoundary() {
        Integer bpmUpperBoundary = Metronome.MAX_BPM_VALUE;

        // try to increment bpm by 100
        for (int i = 0; i < 200; i++) {
            metronome.incrementBPMByFive();
        }

        assertEquals(bpmUpperBoundary.intValue(), metronome.getBPM().intValue());
    }

    @Test
    public void incrementBPMByOneCannotGoHigherThanUpperBoundary() {
        Integer bpmUpperBoundary = Metronome.MAX_BPM_VALUE;

        // try to increment bpm by 200
        for (int i = 0; i < 200; i++) {
            metronome.incrementBPMByOne();
        }

        assertEquals(bpmUpperBoundary.intValue(), metronome.getBPM().intValue());
    }
}