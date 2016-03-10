package preferences;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import command.Command;

public class saveState implements java.io.Serializable {
	private static final long serialVersionUID = 7735208842814805719L;
	private int backColorIndex;
	private Map<Double, String> colorMap;
	private String imageFile;
	private Integer turtleNumber;
	private String language;
	private double backColor;
	//TODO
	private int penColor;
	private List<String> history;
	private Map<String, Double> variables = new HashMap<String, Double>();
	private Map<Double, String> images;
	public Map<String, List<Command>> commands;	
	
	public Map<Double, String> getColorMap() {
		return colorMap;
	}
	
	public void setColorMap(Map<Double, String> colorMap) {
		this.colorMap = colorMap;
	}
	
	public int getBackColorIndex() {
		return backColorIndex;
	}
	
	public void setBackColorIndex(int backColorIndex) {
		this.backColorIndex = backColorIndex;
	}
	
	public Map<Double, String> getImages() {
		return images;
	}
	
	public void setImages(Map<Double, String> images) {
		this.images = images;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public Integer getTurtleNumber() {
		return turtleNumber;
	}
	
	public void setTurtleNumber(Integer turtleNumber) {
		this.turtleNumber = turtleNumber;
	}
	
	public String getImageFile() {
		return imageFile;
	}
	
	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}

	public double getBackColor() {
		return backColor;
	}

	public void setBackColor(double backColor) {
		this.backColor = backColor;
	}

	public List<String> getHistory() {
		return history;
	}

	public void setHistory(List<String> history) {
		this.history = history;
	}

	public HashMap<String, Double> getVariables() {
		return (HashMap<String, Double>) variables;
	}

	public void setVariables(Map<String, Double> variables) {
		this.variables = variables;
	}
}
