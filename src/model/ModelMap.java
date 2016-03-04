package model;

import java.util.List;
import java.util.Map;
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
                    TurtleModel newTurtle = turtles.get(0).makeNewActiveTurtle(turtles.get(0));
                    TurtleView newView = turtleViews.get(0).makeNewTurtleView(turtleViews.get(0));
                    turtles.add(newTurtle);
                    turtleViews.add(newView);
                    newTurtle.addObserver(newView);
                }
            }
            turtles.get((int) values[i]-1).setActive(1);
        }
        lastActiveID = (int) values[values.length-1];
        return lastActiveID;
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

    public double TurtleAction (String command, double[] parameter) {
        lastValue = 0;
        turtles.stream().filter(t -> t.isActive()).forEach(t -> {
            try {
                if (parameter[0] == -1) {
                    lastValue = (double) t.getClass().getDeclaredMethod(command).invoke(t);
                }
                else {
                    lastValue =
                            (double) t.getClass().getDeclaredMethod(command, double[].class)
                                    .invoke(t, parameter);
                }
                lastActiveID = turtles.indexOf(t);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
        return lastValue;
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
