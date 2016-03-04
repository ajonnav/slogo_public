package model;

import java.util.List;
import java.util.Map;
import java.util.Observable;

public class DisplayModel extends Observable{
    
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
        setChanged();
    }
    
    public double getBackgroundColorIndex () {
        return backgroundColorIndex;
    }
    
    public double TurtleAction (String command, double[] parameter) {
        lastValue = 0;
        turtleList.stream().filter(t -> t.isActive()).forEach(t -> {
            try {
                if (parameter[0] == -1) {
                    lastValue = (double) t.getClass().getDeclaredMethod(command).invoke(t);
                }
                else {
                    lastValue =
                            (double) t.getClass().getDeclaredMethod(command, double[].class)
                                    .invoke(t, parameter);
                }
                lastActiveID = turtleList.indexOf(t);
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
                    TurtleModel newTurtle = turtleList.get(0).makeNewActiveTurtle(turtleList.get(0));
                    turtleList.add(newTurtle);
                }
            }
            turtleList.get((int) values[i]-1).setActive(1);
        }
        lastActiveID = (int) values[values.length-1];
        return lastActiveID;
    }
    
    public double addToColorMap (double[] values) {
        colorMap.put(values[0], String.format("#%02X%02X%02X", (int) values[1], (int) values[2],
                                              (int) values[3]));
        turtleList.stream().forEach(t -> {
            try {
                t.setColorMap(colorMap);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
        setColorMap(colorMap);
        return values[0];
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
    
}
