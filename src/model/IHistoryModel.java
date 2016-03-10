package model;

import java.util.List;
import java.util.Observable;

public abstract class IHistoryModel extends Observable{

	public abstract void addToHistory (String command);
	
	public abstract void clearHistory ();
	
	public abstract List<String> getImmutableHistoryList ();
}
