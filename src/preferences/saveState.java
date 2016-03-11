package preferences;

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
	private List<String> history;
	private Map<String, Double> variables;
	private Map<Double, String> images;
	//private Map<String, List<Command>> commands;
	//private Map<String, List<Command>> commandVars;
	
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

	public Map<String, Double> getVariables() {
		return (Map<String, Double>) variables;
	}

	public void setVariables(Map<String, Double> variables) {
		this.variables = variables;
	}
	/*
	public Map<String, List<Command>> getCommands() {
		return commands;
	}

	public void setCommands(Map<String, List<Command>> commands) {
		this.commands = commands;
	}

	public Map<String, List<Command>> getCommandVars() {
		return commandVars;
	}

	public void setCommandVars(Map<String, List<Command>> commandVars) {
		this.commandVars = commandVars;
	}
	*/
}
