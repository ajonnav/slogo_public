package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.layout.HBox;
import command.Command;
import constants.UIConstants;
import view.CoordinateView;
import view.TurtleView;

public class DisplayModel extends Observable implements Observer{
    
    private double backgroundColorIndex;
    private Map<Double, String> imageMap;
    private Map<Double, String> colorMap;
    private double lastValue;
    private List<TurtleModel> turtleList;
    private int lastActiveID;
    
    public DisplayModel(Map<Double, String> colorMap, Map<Double, String> imageMap) {
        this.colorMap = colorMap;
        backgroundColorIndex = 3;
        this.lastActiveID = 1;
        this.colorMap = colorMap;
        this.imageMap = imageMap;
        turtleList = new ArrayList<>();
        setTurtle();
        setChanged();
    }
    
    private void setTurtle () {
        turtleList = new ArrayList<TurtleModel>();
        for(int i = 0; i < 3; i++) {
            TurtleModel turtleModel = new TurtleModel(0, 0, UIConstants.INITIAL_HEADING);
            setImage(turtleModel);
            turtleModel.penDown();
            turtleModel.addObserver(this);
            turtleModel.setImageString(imageMap.get(turtleModel.getImageIndex()));
            turtleModel.setPenColorString(colorMap.get(turtleModel.getPenColorIndex()));
            turtleList.add(turtleModel);
            updateView();
        }
    }
    
    
    public double getBackgroundColorIndex () {
        return backgroundColorIndex;
    }
    
    
    public double TurtleAction (String command, List<Command> parameters) {
    	 lastValue = 0;
         turtleList.stream().filter(t -> t.isActive()).
         forEach(turtle -> {
             try {
                 lastActiveID = turtleList.indexOf(turtle) + 1;
                 if (parameters == null) {
                     lastValue = (double) turtle.getClass().getDeclaredMethod(command).invoke(turtle);
                 }
                 else {  
                     lastValue =
                             (double) turtle.getClass().getDeclaredMethod(command, double[].class)
                                     .invoke(turtle, commandsToDoubleArray(parameters));
                 }
             }
             catch (Exception e) {
                 e.printStackTrace();
             }
         });
         return lastValue;
    }
    
    
    public double tell (double[] values) {
    	turtleList.stream().forEach(t -> t.setActive(0));
        for(int i = 0; i < values.length; i++) {
            if(values[i] > turtleList.size()) {
                int currSize = turtleList.size();
                for(int j = 0; j < values[i] - currSize; j++) {
                    TurtleModel newTurtle = turtleList.get(0).makeNewActiveTurtle();
                    turtleList.add(newTurtle);
                }
            }
            turtleList.get((int) values[i]-1).setActive(1);
        }
        if(values.length != 0) {
            lastActiveID = (int) values[values.length-1];
        }
        return lastActiveID;
    }
    
    
    public double[] getActiveTurtleIDs() {
        List<Double> active = new ArrayList<Double>();
        for(int i = 0; i < turtleList.size(); i++) {
            if(turtleList.get(i).isActive()) {
                active.add((double) i + 1);
            }
        }
        return active.stream().mapToDouble(d -> d).toArray();
    }
    
    
    public double addToColorMap (double[] values) {
        colorMap.put(values[0], String.format("#%02X%02X%02X", (int) values[1], (int) values[2],
                                              (int) values[3]));
        setColorMap(colorMap);
        return values[0];
    }
    
    
    public double[] commandsToDoubleArray(List<Command> parameters) {
        double[] array = new double[parameters.size()];
        for(int i = 0; i < parameters.size(); i++) {
            array[i] = parameters.get(i).execute();
        }
        return array;
    }

    
    public double setBackgroundColorIndex (double backgroundColorIndex) {
        this.backgroundColorIndex = backgroundColorIndex;
        updateView();
        return backgroundColorIndex;
    }
    
    
    public Map<Double, String> getColorMap() {
        return colorMap;
    }
    
    
    public void setColorMap(Map<Double, String> colorMap) {
        this.colorMap = colorMap;
        updateView();
    }
    
    
    public String getBackgroundColor() {
    	return colorMap.get(backgroundColorIndex);
    }
        
    
    public void updateView() {
        setChanged();
        notifyObservers();
    }
    
    
    public int getLastActiveID() {
        return lastActiveID;
    }
    
    
    public Map<Double, String> getImageMap () {
        return imageMap;
    }
    
    
    public int getNumTurtles() {
        return turtleList.size();
    }
    
    
    public void setTurtles (List<TurtleModel> turtles) {
        this.turtleList = turtles;
    }
    
    
    public List<TurtleModel> getTurtleList() {
    	return new ArrayList<TurtleModel>(turtleList);
    }

    private void setImage(TurtleModel turtleModel) {
    	turtleModel.setImageString(imageMap.get(turtleModel.getImageIndex()));
    }

	@Override
	public void update(Observable o, Object arg) {
		turtleList.stream().forEach(t->
		{
			t.addObserver(this);
			t.setImageString(imageMap.get(t.getImageIndex()));
			t.setPenColorString(colorMap.get(t.getPenColorIndex()));
		});
		updateView();
	}
}
