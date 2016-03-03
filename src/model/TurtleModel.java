package model;

import java.util.Map;
import java.util.Observable;


public class TurtleModel extends Observable {
    private double heading;
    private double positionX;
    private double positionY;
    private boolean penStatus;
    private boolean showStatus;
    private boolean shouldClear;
    private boolean shouldStamp;
    private boolean shouldClearStamp;
    private int numStamps;
    private double penColorIndex;
    private double imageIndex;
    private double lineWidth;
    private Map<Double, String> colorMap;
    private Map<Double, String> imageMap;

    public TurtleModel (double turtleInitialX, double turtleInitialY, double turtleInitialHeading, 
                        Map<Double, String> colorMap, Map<Double, String> imageMap) {
        heading = turtleInitialHeading;
        positionX = turtleInitialX;
        positionY = turtleInitialY;
        penStatus = false;
        showStatus = true;
        shouldClear = false;
        shouldStamp = false;
        numStamps = 0;
        penColorIndex = 1;
        imageIndex = 5;
        lineWidth = 1;
        this.colorMap = colorMap;
        this.imageMap = imageMap;
        setChanged();
    }


    public void forward (double distance) {
        positionX += distance * Math.cos(Math.toRadians(heading));
        positionY += distance * Math.sin(Math.toRadians(heading));
        updateView();
    }

    public void setHeading (double degrees) {
        heading = degrees;
        updateView();
    }

    public void setPosition (double x, double y) {
        positionX = x;
        positionY = y;
        updateView();
    }

    public void turn (double degree) {
        setHeading(heading - degree);
    }

    public void penUp () {
        penStatus = false;
    }

    public void penDown () {
        penStatus = true;
    }

    public void show () {
        showStatus = true;
        updateView();
    }

    public void hide () {
        showStatus = false;
        updateView();
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

    public boolean getPenStatus () {
        return penStatus;
    }

    public boolean getShowStatus () {
        return showStatus;
    }

    public boolean shouldClear () {
        return shouldClear;
    }
    
    public void setShouldClear(boolean shouldClear) {
        this.shouldClear = shouldClear;
        updateView();
    }
    
    public void addToColorMap(double index, double r, double g, double b) {
        colorMap.put(index, String.format("#%02X%02X%02X", (int) r, (int) g, (int) b));
        updateView();
    }
    
    public Map<Double, String> getColorMap() {
        return colorMap;
    }

    public Map<Double, String> getImageMap () {
        return imageMap;
    }

    public double getPenColorIndex () {
        return penColorIndex;
    }

    public void setPenColorIndex (double penColorIndex) {
        this.penColorIndex = penColorIndex;
        updateView();
    }

    public double getImageIndex () {
        return imageIndex;
    }

    public void setImageIndex (double imageIndex) {
        this.imageIndex = imageIndex;
        updateView();
    }

    public double getLineWidth () {
        return lineWidth;
    }

    public void setLineWidth (double lineWidth) {
        this.lineWidth = lineWidth;
        updateView();
    }

    public boolean shouldStamp () {
        return shouldStamp;
    }

    public void setShouldStamp (boolean shouldStamp) {
        this.shouldStamp = shouldStamp;
        updateView();
    }
    
    public int getNumStamps() {
        return numStamps;
    }
    
    public void setNumStamps(int numStamps) {
        this.numStamps = numStamps;
        updateView();
    }

    public boolean shouldClearStamp () {
        return shouldClearStamp;
    }

    public void setShouldClearStamp (boolean shouldClearStamp) {
        this.shouldClearStamp = shouldClearStamp;
        updateView();
    }
    
    public void updateView() {
        setChanged();
        notifyObservers();
    }

}
