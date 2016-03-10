package preferences;

import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;

public class saveState implements java.io.Serializable {
	public int backColorIndex;
	public Map<Double, String> colorMap;
	public String imageFile;
	public Integer turtleNumber;
	public String language;
	public String loadFile;
	public List<String> history;
	public Map<String, Double> variables;
	public Map<Double, String> images;
	//public Map<String, List<Commands>> commands;
	
}
