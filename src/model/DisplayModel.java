package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import command.Command;
import constants.UIConstants;

public class DisplayModel extends Observable{
    
    private double backgroundColorIndex;
    private Map<Double, String> imageMap;
    private Map<Double, String> colorMap;
    private double lastValue;
    private List<List<TurtleModel>> stateList;
    private int lastActiveID;
    private boolean toAnimate;
    
    public DisplayModel(Map<Double, String> colorMap, Map<Double, String> imageMap) {
        this.colorMap = colorMap;
        backgroundColorIndex = 3;
        this.lastActiveID = 1;
        this.colorMap = colorMap;
        this.imageMap = imageMap;
        this.toAnimate = true;
        stateList = new ArrayList<>();
        setTurtles();
        setChanged();
    }
    
    private void setTurtles () {
        List<TurtleModel> firstTurtleList = new ArrayList<TurtleModel>();
        stateList.add(firstTurtleList);
        stateList.add(firstTurtleList);
        for(int i = 0; i < 3; i++) {
            TurtleModel turtleModel = new TurtleModel(0, 0, UIConstants.INITIAL_HEADING);
            turtleModel.penDown();
            turtleModel.setImageString(imageMap.get(turtleModel.getImageIndex()));
            turtleModel.setPenColorString(colorMap.get(turtleModel.getPenColorIndex()));
            firstTurtleList.add(turtleModel);
        }
    }
    
    public double getBackgroundColorIndex () {
        return backgroundColorIndex;
    }
    
    public double TurtleAction (String command, List<Command> parameters) {
    	 lastValue = 0;
    	 List<TurtleModel> nextTurtleList = makeCopyOfTurtleList(stateList.get(stateList.size()-1));       
         nextTurtleList.stream().filter(t -> t.isActive()).
         forEach(turtle -> {
             try {
                 System.out.println(turtle.getPositionY());
                 lastActiveID = nextTurtleList.indexOf(turtle) + 1;
                 if (parameters == null) {
                     lastValue = (double) turtle.getClass().getDeclaredMethod(command).invoke(turtle);
                 }
                 else {  
                     lastValue =
                             (double) turtle.getClass().getDeclaredMethod(command, double[].class)
                                     .invoke(turtle, commandsToDoubleArray(parameters));
                 }
                 System.out.println(turtle.getPositionY());
             }
             catch (Exception e) {
                 e.printStackTrace();
             }
         });
         stateList.add(nextTurtleList);
         updateDisplay();
         return lastValue;
    }
    
    public double tell (double[] values) {
    	List<TurtleModel> nextTurtleList = makeCopyOfTurtleList(stateList.get(stateList.size()-1));
        stateList.add(nextTurtleList);
    	nextTurtleList.stream().forEach(t -> t.setActive(0));
        for(int i = 0; i < values.length; i++) {
            if(values[i] > nextTurtleList.size()) {
                int currSize = nextTurtleList.size();
                for(int j = 0; j < values[i] - currSize; j++) {
                    TurtleModel newTurtle = nextTurtleList.get(0).makeNewActiveTurtle();
                    nextTurtleList.add(newTurtle);
                }
            }
            nextTurtleList.get((int) values[i]-1).setActive(1);
        }
        if(values.length != 0) {
            lastActiveID = (int) values[values.length-1];
        }
        updateView();
        return lastActiveID;
    }
    
    public double[] getActiveTurtleIDs() {
        List<Double> active = new ArrayList<Double>();
        for(int i = 0; i < stateList.get(stateList.size()-1).size(); i++) {
            if(stateList.get(stateList.size()-1).get(i).isActive()) {
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
        return stateList.get(stateList.size()-1).size();
    }
    
    public List<TurtleModel> getNextTurtleList() {
    	return stateList.get(stateList.size()-1);
    }
    
    public List<TurtleModel> getPrevTurtleList() {
    	return stateList.get(stateList.size()-2);
    }

	public void updateDisplay() {
		stateList.get(stateList.size()-1).stream().forEach(t->
		{
			t.setImageString(imageMap.get(t.getImageIndex()));
			t.setPenColorString(colorMap.get(t.getPenColorIndex()));
		});
		updateView();
	}
	
	public List<TurtleModel> makeCopyOfTurtleList(List<TurtleModel> turtleList) {
		List<TurtleModel> newList = new ArrayList<TurtleModel>();
		for(TurtleModel t : turtleList) {
			TurtleModel newTurtle = makeCopyOfTurtleModel(t);
			newList.add(newTurtle);
		}
		return newList;
	}
	
	private TurtleModel makeCopyOfTurtleModel(TurtleModel turtle) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(turtle);
			oos.flush();
			oos.close();
			bos.close();
			byte[] byteData = bos.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
			return (TurtleModel) new ObjectInputStream(bais).readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getNumFrames() {
		return stateList.size();
	}
	
	public List<TurtleModel> getFrame(int frameNumber) {
		return stateList.get(frameNumber);
	}
	
    public boolean isToAnimate () {
        return toAnimate;
    }

    public void setToAnimate (boolean toAnimate) {
        this.toAnimate = toAnimate;
    }
}
