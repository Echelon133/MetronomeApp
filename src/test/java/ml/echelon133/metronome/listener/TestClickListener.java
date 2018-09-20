package ml.echelon133.metronome.listener;

import ml.echelon133.metronome.event.ClickEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TestClickListener implements IClickListener {

    private List<ClickEvent> clickEventList = new LinkedList<>();

    @Override
    public void newClickEvent(ClickEvent clickEvent) {
        clickEventList.add(clickEvent);
    }

    public Integer getNumberOfClicks() {
        return clickEventList.size();
    }

    public Set<Integer> getAllAccentIntervals() {
        return clickEventList
                .stream()
                .map(ClickEvent::getAccentInterval)
                .collect(Collectors.toSet());
    }
}
