package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import command.Command;
import constants.UIConstants;
import exception.SLogoException;


public class DisplayModel extends IDisplayModel{

    private double backgroundColorIndex;
    private Map<Double, String> imageMap;
    private Map<Double, String> colorMap;
    private double lastValue;
    private List<TurtleModel> turtleList;
    private int lastActiveID;
    private boolean toAnimate;
    private ResourceBundle errorBundle = ResourceBundle.getBundle(UIConstants.DEFAULT_RESOURCE + UIConstants.ERRORS);

    public DisplayModel (Map<Double, String> colorMap, Map<Double, String> imageMap) {
        this.colorMap = colorMap;
        backgroundColorIndex = 3;
        this.lastActiveID = 1;
        this.colorMap = colorMap;
        this.imageMap = imageMap;
        this.toAnimate = true;
        turtleList = new ArrayList<>();
        setTurtles();
        setChanged();
        updateView();
    }

    private void setTurtles () {
        for (int i = 0; i < 3; i++) {
            TurtleModel turtleModel = new TurtleModel(0, 0, UIConstants.INITIAL_HEADING, imageMap, colorMap);
            turtleList.add(turtleModel);
        }
    }

    @Override
    public double getBackgroundColorIndex () {
        return backgroundColorIndex;
    }

    @Override
    public double TurtleAction (String command, List<Command> parameters) {
        lastValue = 0;
        turtleList.stream().filter(t -> t.isActive()).forEach(turtle -> {
            try {
                lastActiveID = turtleList.indexOf(turtle) + 1;
                if (parameters == null) {
                    lastValue =
                            (double) turtle.getClass().getDeclaredMethod(command).invoke(turtle);
                }
                else {
                    lastValue =
                            (double) turtle.getClass().getDeclaredMethod(command, double[].class)
                                    .invoke(turtle, commandsToDoubleArray(parameters));
                }
            }
            catch (Exception e) {
               new SLogoException(errorBundle.getString("ReflectionError"));
            }
        });
        turtleList.stream().forEach(turtle -> turtle.syncFrame());
        updateView();
        return lastValue;
    }

    @Override
    public double tell (double[] values) {
        turtleList.stream().forEach(t -> {
            t.setActive(false);
            t.syncFrame();
        });
        for (int i = 0; i < values.length; i++) {
        	if(values[i] <0) {
        		throw new SLogoException(errorBundle.getString("InvalidIndex"));
        	}
        	
            if (values[i] > turtleList.size()) {
                int currSize = turtleList.size();
                for (int j = 0; j < values[i] - currSize; j++) {
                    TurtleModel newTurtle = turtleList.get(0).makeNewActiveTurtle();
                    turtleList.add(newTurtle);
                }
            }
            turtleList.get((int) values[i] - 1).setActive(true);
        }
        if (values.length != 0) {
            lastActiveID = (int) values[values.length - 1];
        }
        turtleList.stream().forEach(t -> { t.syncFrame(); });
        updateView();
        return lastActiveID;
    }

    @Override
    public double[] getActiveTurtleIDs () {
        List<Double> active = new ArrayList<Double>();
        for (int i = 0; i < turtleList.size(); i++) {
            if (turtleList.get(i).isActive()) {
                active.add((double) i + 1);
            }
        }
        return active.stream().mapToDouble(d -> d).toArray();
    }

    @Override
    public double addToColorMap (double[] values) {
        colorMap.put(values[0], String.format("#%02X%02X%02X", (int) values[1], (int) values[2],
                                              (int) values[3]));
        setColorMap(colorMap);
        return values[0];
    }

    private double[] commandsToDoubleArray (List<Command> parameters) {
        double[] array = new double[parameters.size()];
        for (int i = 0; i < parameters.size(); i++) {
            array[i] = parameters.get(i).execute();
        }
        return array;
    }
    
    @Override
    public double setBackgroundColorIndex (double backgroundColorIndex) {
    	if(!colorMap.containsKey(backgroundColorIndex)) {
    		throw new SLogoException(errorBundle.getString("InvalidIndex"));
    	}
        this.backgroundColorIndex = backgroundColorIndex;
        return backgroundColorIndex;
    }

    @Override
    public Map<Double, String> getColorMap () {
        return colorMap;
    }

    @Override
    public void setColorMap (Map<Double, String> colorMap) {
        this.colorMap = colorMap;
        turtleList.stream().forEach(t -> t.setColorMap(colorMap));
    }

    @Override
    public void updateView () {
        setChanged();
        notifyObservers();
    }

    @Override
    public int getLastActiveID () {
        return lastActiveID;
    }

    @Override
    public Map<Double, String> getImageMap () {
        return imageMap;
    }
    
    @Override
    public List<TurtleModel> getTurtleList() {
        return turtleList;
    }
    
    @Override
	public List<ViewableTurtleModel> getViewableTurtleList() {
    	List<ViewableTurtleModel> returnList = turtleList.stream()
    			.map(t->(ViewableTurtleModel)t)
    			.collect(Collectors.toList());
		return returnList;
	}

    @Override
    public int getNumTurtles () {
        return turtleList.size();
    }

    @Override
    public boolean isToAnimate () {
        return toAnimate;
    }

    @Override
    public void setToAnimate (boolean toAnimate) {
        this.toAnimate = toAnimate;
    }
    
    public ViewableTurtleModel randomMethod(TurtleModel turtle) {
    	return turtle;
    }
    
}