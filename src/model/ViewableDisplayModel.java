package model;

import java.util.List;
import java.util.Map;

public interface ViewableDisplayModel {

	public abstract double setBackgroundColorIndex (double backgroundColorIndex);
	
	public abstract double getBackgroundColorIndex();
	
	public abstract Map<Double, String> getImageMap ();
	
	public abstract void updateView ();
	
	public abstract boolean isToAnimate ();
	
	public abstract void setToAnimate (boolean toAnimate);
	
	public abstract double[] getActiveTurtleIDs();
	
	public abstract int getLastActiveID();
	
	public abstract int getNumTurtles();
	
	public abstract List<ViewableTurtleModel> getViewableTurtleList();
	
	public abstract Map<Double, String> getColorMap();
}
