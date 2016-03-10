package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HistoryModel extends IHistoryModel {

    private List<String> history;

    public HistoryModel () {
        history = new ArrayList<>();
    }

    public void addToHistory (String command) {
        history.add(command);
        setChanged();
        notifyObservers();
    }

    public void clearHistory () {
        history.clear();
        setChanged();
        notifyObservers();
    }

    public List<String> getImmutableHistoryList () {
        return Collections.unmodifiableList(history);
    }

}
