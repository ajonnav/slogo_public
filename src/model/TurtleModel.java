package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import com.sun.prism.paint.Color;

import javafx.scene.image.ImageView;


public class TurtleModel extends Observable implements Observer{
    
    private double turtleInitialX;
    private double turtleInitialY;
    private double turtleInitialHeading;
    private String imageString;
    private PenModel pen;
    private boolean isActive;
    private double heading;
    private double positionX;
    private double positionY;
    private boolean showStatus;
    private double imageIndex;
    private List<LineModel> lineList;
    private List<StampModel> stampList;

    public TurtleModel (double turtleInitialX, double turtleInitialY, double turtleInitialHeading) {
        isActive = false;
        heading = turtleInitialHeading;
        this.turtleInitialHeading = turtleInitialHeading;
        positionX = turtleInitialX;
        this.turtleInitialX = turtleInitialX;
        positionY = turtleInitialY;
        this.turtleInitialY = turtleInitialY;
        showStatus = true;
        imageIndex = 5;
        lineList = new ArrayList<>();
        stampList=  new ArrayList<>();
        double penColorIndex = 1.0;
        double lineWidth = 1;
        double style = 0;
        pen = new PenModel(false, lineWidth, penColorIndex, style);
        pen.addObserver(this);
        setChanged();
    }
    
    public TurtleModel makeNewActiveTurtle() {
        TurtleModel newTurtle = new TurtleModel(turtleInitialX, turtleInitialY, turtleInitialHeading);
        newTurtle.isActive = true;
        return newTurtle;
    }

    public double forward (double[] distance) {
    	if(pen.getStatus()) {
	    	lineList.add(new LineModel(positionX, positionY, 
	    			positionX+distance[0] * Math.cos(Math.toRadians(heading)), 
	    			positionY + distance[0] * Math.sin(Math.toRadians(heading)),
	    			pen.getSize(), pen.getColorString(), pen.getStyle()));
    	}
        positionX += distance[0] * Math.cos(Math.toRadians(heading));
        positionY += distance[0] * Math.sin(Math.toRadians(heading));
        updateView();
        return distance[0];
    }
    
    public double backward (double[] distance) {
        distance[0] = -distance[0];
        return forward(distance);
    }

    public void setHeading (double[] degrees) {
        heading = degrees[0];
        updateView();
    }

    public double turnRight (double[] degree) {
        heading -= degree[0];
        updateView();
        return degree[0];
    }

    public double turnLeft (double[] degree) {
        degree[0] = -degree[0];
        return turnRight(degree);
    }

    public double penUp () {
        pen.setStatus(false);
        updateView();
        return 0;
    }
    
    public double penDown () {
    	pen.setStatus(true);
        updateView();
        return 1;
    }
    
    public double stamp () {
        stampList.add(new StampModel(imageString, positionX, positionY));
        updateView();
        return imageIndex;
    }
    
    public double show () {
        showStatus = true;
        updateView();
        return 1;
    }
    
    public double hide () {
        showStatus = false;
        updateView();
        return 0;
    }
    
    public double home () {
        double returnValue = Math.sqrt(Math.pow((0 - positionX), 2) +
                                       Math.pow((0 - positionY), 2));
        positionX = 0;
        positionY = 0;
        heading = 90;
        updateView();
        return returnValue;
    }
    
    public double setPosition(double[] xy) {
        double[] oldPos = new double[]{positionX, positionY};
        positionX = xy[0];
        positionY = xy[1];
        updateView();
        return Math.sqrt(Math.pow((oldPos[0] - positionX), 2) +
                         Math.pow((oldPos[1] - positionY), 2));
    }
    
    public double setTowards(double[] xy) {
        double lastHeading = heading;
        if(xy[0] == positionX && xy[1] == positionY) {
            return 0;
        }
        double rawDegrees = Math.toDegrees(Math.atan((xy[1] - positionY) / (xy[0] - positionX)));
        double newHeading = xy[0] - positionX >= 0 ? rawDegrees : rawDegrees - 180;
        heading = newHeading;
        double headingDiff = Math.abs(lastHeading - heading);
        updateView();
        return headingDiff >= 180 ? 360 - headingDiff : headingDiff;
    }
    
    public double getPositionY () {
        return positionY;
    }
    
    public double getPositionX () {
        return positionX;
    }
    
    public double getHeading () {
        return heading;
    }
    
    public double getPenStatus () {
        return pen.getStatus() ? 1 : 0;
    }

    public double getShowStatus () {
        return showStatus ? 1 : 0;
    }
    
    public double clearScreen() {
        double returnValue = home();
        stampList.clear();
        lineList.clear();
        updateView();
        return returnValue;
    }

    public double getPenColorIndex () {
        return pen.getColorIndex();
    }
    
    public double setPenColorIndex (double[] penColorIndex) {
        this.pen.setColorIndex(penColorIndex[0]);
        updateView();
        return penColorIndex[0];
    }
    
    public void setPenColorString(String colorString) {
    	pen.setColorString(colorString);
    }
    
    public double getImageIndex () {
        return imageIndex;
    }
    
    public double setImageIndex (double[] imageIndex) {
        this.imageIndex = imageIndex[0];
        updateView();
        return imageIndex[0];
    }
    
    public double getLineWidth () {
        return pen.getSize();
    }

    public double setLineWidth (double[] lineWidth) {
        this.pen.setSize(lineWidth[0]);
        updateView();
        return lineWidth[0];
    }
    
    public int getNumStamps() {
        return stampList.size();
    }
    
    public boolean isActive () {
        return isActive;
    }

    public void setActive (double isActive) {
        this.isActive = isActive == 1 ? true : false;
    }

    public String getImageString() {
    	return imageString;
    }
    
    public void setImageString(String imageString) {
    	this.imageString = imageString;
    }
    
    public List<LineModel> getLineList() {
    	return lineList;
    }
    
    public List<StampModel> getStampList() {
    	return stampList;
    }
    
    public void updateView() {
        setChanged();
        notifyObservers();
    }

	
    @Override
	public void update(Observable o, Object arg) {
		updateView();
	}
    
}