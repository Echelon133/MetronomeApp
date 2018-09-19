package ml.echelon133.metronome;

import ml.echelon133.metronome.event.*;
import ml.echelon133.metronome.listener.IClickListener;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;

public class Metronome implements IMetronome, Runnable, IClickSource {
    private Lock lock = new ReentrantLock();
    private Condition ready = lock.newCondition();

    private AtomicInteger counter;
    private Integer beatsPerMinute;
    private Integer accentInterval;

    private ClickEventHandler handler;

    private Boolean done;

    public Metronome() {
        beatsPerMinute = 80;
        counter = new AtomicInteger(1);
        handler = new ClickEventHandler();
        accentInterval = 4;
    }

    @Override
    public void resetCounter() {
        counter = new AtomicInteger(1);
    }

    @Override
    public void run() {
        try {
            lock.lock();
            while (true) {
                try {
                    if (done) {
                        ready.await();
                    } else {
                        signalClick();
                        ready.await(calculatePause(), TimeUnit.MILLISECONDS);
                    }
                } catch (InterruptedException ex) {
                    return;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void signalClick() {
        handler.signalClick(counter.getAndIncrement(), accentInterval);
    }

    public void setDone(Boolean d) {
        try {
            lock.lock();
            done = d;
            if (!done) {
                ready.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void incrementBPMByOne() {
        beatsPerMinute += 1;
    }

    @Override
    public void decrementBPMByOne() {
        beatsPerMinute -= 1;
    }

    @Override
    public void incrementBPMByFive() {
        beatsPerMinute += 5;
    }

    @Override
    public void decrementBPMByFive() {
        beatsPerMinute -= 5;
    }

    @Override
    public long calculatePause() {
        // calculate how many milliseconds there are between clicks
        // 60 bpm -> click every 1000ms
        // 120 bpm -> click every 500ms
        return (long) ((60.0 / beatsPerMinute) * 1000);
    }

    @Override
    public Integer getBPM() {
        return beatsPerMinute;
    }


    @Override
    public void setAccentInterval(Integer accentInterval) {
        this.accentInterval = accentInterval;
    }

    @Override
    public void addClickListener(IClickListener cl) {
        handler.addClickListener(cl);
    }

    @Override
    public void removeClickListener(IClickListener cl) {
        handler.removeClickListener(cl);
    }
}
