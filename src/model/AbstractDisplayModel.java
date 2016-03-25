package model;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import command.Command;

public abstract class AbstractDisplayModel extends Observable implements ViewableDisplayModel{
	
	public abstract double TurtleAction (String command, List<Command> parameters);
	
	public abstract double tell (double[] values);
	
	public abstract double[] getActiveTurtleIDs();
	
	public abstract int getLastActiveID();
	
	public abstract int getNumTurtles();
	
	public abstract List<TurtleModel> getTurtleList();
	
	public abstract List<ViewableTurtleModel> getViewableTurtleList();
	
	public abstract Map<Double, String> getColorMap();
	
	public abstract void setColorMap(Map<Double, String> colorMap);
	
	public abstract double addToColorMap (double[] values);
	
	public abstract double setBackgroundColorIndex (double backgroundColorIndex);
	
	public abstract double getBackgroundColorIndex();
	
	public abstract Map<Double, String> getImageMap ();
	
	public abstract void updateView ();
	
	public abstract boolean isToAnimate ();
	
	public abstract void setToAnimate (boolean toAnimate);
	
	public abstract boolean isToUpdateIDView ();

        public abstract void setIsToUpdateIDView (boolean toUpdateIDView);

	public abstract double setAnimationSpeed(double[] ds);
}
