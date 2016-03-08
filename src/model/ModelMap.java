package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import command.Command;
import view.TurtleView;


public class ModelMap {

    private List<TurtleModel> turtles;
    private List<TurtleView> turtleViews;
    private HistoryPaneModel history;
    private CommandsModel commands;
    private VariableModel variable;
    private DisplayModel display;
    private double lastValue;
    private int lastActiveID;
    private Map<Double, String> colorMap;
    private Map<Double, String> imageMap;

    public ModelMap (Map<Double, String> colorMap, Map<Double, String> imageMap) {
        this.colorMap = colorMap;
        this.imageMap = imageMap;
        this.lastActiveID = 1;
    }

    public Map<Double, String> getColorMap () {
        return colorMap;
    }
    
    public double tell (double[] values) {
        turtles.stream().forEach(t -> t.setActive(0));
        for(int i = 0; i < values.length; i++) {
            if(values[i] > turtles.size()) {
                int currSize = turtles.size();
                for(int j = 0; j < values[i] - currSize; j++) {
                    TurtleModel newTurtle = turtles.get(0).makeNewActiveTurtle();
                    TurtleView newView = turtleViews.get(0).makeNewTurtleView();
                    turtles.add(newTurtle);
                    turtleViews.add(newView);
                    newTurtle.addObserver(newView);
                    try {
                        newTurtle.getClass().getDeclaredMethod("updateView").invoke(newTurtle);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            turtles.get((int) values[i]-1).setActive(1);
        }
        if(values.length != 0) {
            lastActiveID = (int) values[values.length-1];
        }
        return lastActiveID;
    }
    
    public double[] getActiveTurtleIDs() {
        List<Double> active = new ArrayList<Double>();
        for(int i = 0; i < turtles.size(); i++) {
            if(turtles.get(i).isActive()) {
                active.add((double) i + 1);
            }
        }
        return active.stream().mapToDouble(d -> d).toArray();
    }
    
    public List<TurtleModel> getImmutableTurtles() {
        return Collections.unmodifiableList(turtles);
    }
    
    
    public void setTurtlesActiveWithCondition() {
        
    }
    
    public void setTurtleViews(List<TurtleView> turtleViews) {
        this.turtleViews = turtleViews;
    }

    public double addToColorMap (double[] values) {
        colorMap.put(values[0], String.format("#%02X%02X%02X", (int) values[1], (int) values[2],
                                              (int) values[3]));
        turtles.stream().forEach(t -> {
            try {
                t.setColorMap(colorMap);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
        display.setColorMap(colorMap);
        return values[0];
    }
    
    public int getLastActiveID() {
        return lastActiveID;
    }

    public Map<Double, String> getImageMap () {
        return imageMap;
    }
    
    public int getNumTurtles() {
        return turtles.size();
    }
    
    public void setTurtles (List<TurtleModel> turtles) {
        this.turtles = turtles;
    }

    public double TurtleAction (String command, List<Command> parameters) {
        lastValue = 0;
        turtles.stream().filter(t -> t.isActive()).forEach(t -> {
            try {
                lastActiveID = turtles.indexOf(t) + 1;
                if (parameters == null) {
                    lastValue = (double) t.getClass().getDeclaredMethod(command).invoke(t);
                }
                else {  
                    lastValue =
                            (double) t.getClass().getDeclaredMethod(command, double[].class)
                                    .invoke(t, commandsToDoubleArray(parameters));
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
        return lastValue;
    }
    
    public double[] commandsToDoubleArray(List<Command> parameters) {
        double[] array = new double[parameters.size()];
        for(int i = 0; i < parameters.size(); i++) {
            array[i] = parameters.get(i).execute();
        }
        return array;
    }

    public void setHistory (HistoryPaneModel history) {
        this.history = history;
    }

    public HistoryPaneModel getHistory () {
        return history;
    }

    public void setCommands (CommandsModel commands) {
        this.commands = commands;
    }

    public CommandsModel getCommands () {
        return commands;
    }

    public void setVariable (VariableModel variable) {
        this.variable = variable;
    }

    public VariableModel getVariable () {
        return variable;
    }

    public void setDisplay (DisplayModel display) {
        this.display = display;
    }

    public DisplayModel getDisplay () {
        return display;
    }
}
