package ml.echelon133.metronome;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

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
}