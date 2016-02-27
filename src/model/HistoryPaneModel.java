package model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

public class HistoryPaneModel extends Observable {

	private List<String> history;
	
	public HistoryPaneModel() {
		history = new ArrayList<>();
	}
	
	public void addToHistory(String command) {
		history.add(command);
		setChanged();
		notifyObservers();
	}
	
	public void clearHistory() {
		history.clear();
		setChanged();
		notifyObservers();
	}
	
	public List<String> getImmutableHistoryList() {
		return Collections.unmodifiableList(history);
	}

}
