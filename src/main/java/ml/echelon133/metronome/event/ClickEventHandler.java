package ml.echelon133.metronome.event;

import ml.echelon133.metronome.listener.IClickListener;

import java.util.Vector;

public class ClickEventHandler {
    private Vector<IClickListener> listeners;

    public ClickEventHandler() {
        this.listeners = new Vector<>();
    }

    public void addClickListener(IClickListener cl) {
        listeners.add(cl);
    }

    public void removeClickListener(IClickListener cl) {
        listeners.remove(cl);
    }

    public void signalClick(Integer clickNumber, Integer accentInterval) {
        ClickEvent clickEvent = new ClickEvent(clickNumber, accentInterval);
        listeners
                .forEach(l -> l.newClickEvent(clickEvent));
    }
}
