package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import command.Command;
import constants.UIConstants;

public class DisplayModel extends Observable implements Observer{
    
    private double backgroundColorIndex;
    private Map<Double, String> imageMap;
    private Map<Double, String> colorMap;
    private double lastValue;
    private List<List<TurtleModel>> stateList;
    private int lastActiveID;
    
    public DisplayModel(Map<Double, String> colorMap, Map<Double, String> imageMap) {
        this.colorMap = colorMap;
        backgroundColorIndex = 3;
        this.lastActiveID = 1;
        this.colorMap = colorMap;
        this.imageMap = imageMap;
        stateList = new ArrayList<>();
        setTurtle();
        setChanged();
    }
    
    private void setTurtle () {
        List<TurtleModel> firstTurtleList = new ArrayList<TurtleModel>();
        for(int i = 0; i < 3; i++) {
            TurtleModel turtleModel = new TurtleModel(0, 0, UIConstants.INITIAL_HEADING);
            setImage(turtleModel);
            turtleModel.penDown();
            turtleModel.addObserver(this);
            turtleModel.setImageString(imageMap.get(turtleModel.getImageIndex()));
            turtleModel.setPenColorString(colorMap.get(turtleModel.getPenColorIndex()));
            turtleModel.addObserver(this);
            firstTurtleList.add(turtleModel);
            updateView();
        }
        stateList.add(firstTurtleList);
        stateList.add(firstTurtleList);
    }
    
    
    public double getBackgroundColorIndex () {
        return backgroundColorIndex;
    }
    
    
    public double TurtleAction (String command, List<Command> parameters) {
    	 lastValue = 0;
    	 List<TurtleModel> nextTurtleList = makeCopyOfTurtleList(stateList.get(stateList.size()-1));
    	 stateList.add(nextTurtleList);
         nextTurtleList.stream().filter(t -> t.isActive()).
         forEach(turtle -> {
             try {
                 lastActiveID = nextTurtleList.indexOf(turtle) + 1;
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
    	List<TurtleModel> nextTurtleList = makeCopyOfTurtleList(stateList.get(stateList.size()-1));
        stateList.add(nextTurtleList);
    	nextTurtleList.stream().forEach(t -> t.setActive(0));
        for(int i = 0; i < values.length; i++) {
            if(values[i] > nextTurtleList.size()) {
                int currSize = nextTurtleList.size();
                for(int j = 0; j < values[i] - currSize; j++) {
                    TurtleModel newTurtle = nextTurtleList.get(0).makeNewActiveTurtle();
                    newTurtle.addObserver(this);
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
    	if(stateList.size() == 1) {
    		return getNextTurtleList();
    	}
    	return stateList.get(stateList.size()-2);
    }

    private void setImage(TurtleModel turtleModel) {
    	turtleModel.setImageString(imageMap.get(turtleModel.getImageIndex()));
    }

	@Override
	public void update(Observable o, Object arg) {
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
			newTurtle.addObserver(this);
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

	public int getNumberOfFrames() {
		return stateList.size();
	}
	
	public List<TurtleModel> getFrame(int frameNumber) {
		return stateList.get(frameNumber);
	}
}
