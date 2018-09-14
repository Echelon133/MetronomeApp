package ml.echelon133.metronome.event;

import ml.echelon133.metronome.listener.IClickListener;

public interface IClickSource {
    void addClickListener(IClickListener cl);
    void removeClickListener(IClickListener cl);
    void signalClick();
}
