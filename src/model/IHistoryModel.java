package model;

import java.util.List;
import java.util.Observable;

public abstract class IHistoryModel extends Observable implements ViewableHistoryModel {

	public abstract void addToHistory (String command);
	
	public abstract void clearHistory ();
	
	public abstract List<String> getImmutableHistoryList ();
	
	public abstract void updateView ();
}
