package model;
import java.util.List;
import java.util.Map;

import command.Command;

public interface IDisplayModel {
	
	public double TurtleAction (String command, List<Command> parameters);
	
	public double tell (double[] values);
	
	public double[] getActiveTurtleIDs();
	
	public int getLastActiveID();
	
	public int getNumTurtles();
	
	public Map<Double, String> getColorMap();
	
	public void setColorMap(Map<Double, String> colorMap);
	
	public double addToColorMap (double[] values);
	
	public double setBackgroundColorIndex (double backgroundColorIndex);
	
	public double getBackgroundColorIndex();
	
	public Map<Double, String> getImageMap ();
	
	public List<TurtleModel> makeCopyOfTurtleList(List<TurtleModel> turtleList);
	
	public void updateView();
}
