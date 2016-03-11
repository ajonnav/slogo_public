package model;
import java.util.List;
import java.util.Map;

import command.Command;

interface IDisplayModel {
	
	double TurtleAction (String command, List<Command> parameters);
	
	double tell (double[] values);
	
	double[] getActiveTurtleIDs();
	
	int getLastActiveID();
	
	int getNumTurtles();
	
	Map<Double, String> getColorMap();
	
	void setColorMap(Map<Double, String> colorMap);
	
	double addToColorMap (double[] values);
	
	double setBackgroundColorIndex (double backgroundColorIndex);
	
	double getBackgroundColorIndex();
	
	Map<Double, String> getImageMap ();
	
	List<TurtleModel> makeCopyOfTurtleList(List<TurtleModel> turtleList);
	
	void updateView();
}
