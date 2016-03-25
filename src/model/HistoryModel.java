package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HistoryModel extends AbstractHistoryModel{

    private List<String> history;

    public HistoryModel () {
        history = new ArrayList<>();
    }

    @Override
    public void addToHistory (String command) {
        history.add(command);
        setChanged();
        notifyObservers();
    }

    @Override
    public void clearHistory () {
        history.clear();
        setChanged();
        notifyObservers();
    }

    @Override
    public List<String> getImmutableHistoryList () {
        return Collections.unmodifiableList(history);
    }
    
    @Override
    public void updateView(){
    	setChanged();
    	notifyObservers();
    }
}
