package model;

import java.util.Map;
import java.util.Observable;


public class TurtleModel extends Observable {
    
    private double turtleInitialX;
    private double turtleInitialY;
    private double turtleInitialHeading;
    private boolean isActive;
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
        isActive = false;
        heading = turtleInitialHeading;
        this.turtleInitialHeading = turtleInitialHeading;
        positionX = turtleInitialX; 
        this.turtleInitialX = turtleInitialX;
        positionY = turtleInitialY;
        this.turtleInitialY = turtleInitialY;
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
    
    public TurtleModel makeNewActiveTurtle(TurtleModel t) {
        TurtleModel newTurtle = new TurtleModel(turtleInitialX, turtleInitialY, turtleInitialHeading, colorMap, imageMap);
        newTurtle.isActive = true;
        return newTurtle;
    }


    public double forward (double[] distance) {
        positionX += distance[0] * Math.cos(Math.toRadians(heading));
        positionY += distance[0] * Math.sin(Math.toRadians(heading));
        updateView();
        return distance[0];
    }

    public void setHeading (double[] degrees) {
        heading = degrees[0];
        updateView();
    }
    

    public double turn (double[] degree) {
        heading -= degree[0];
        updateView();
        return degree[0];
    }

    public double penUp () {
        penStatus = false;
        updateView();
        return 0;
    }

    public double penDown () {
        penStatus = true;
        updateView();
        return 1;
    }
    
    public double stamp () {
        this.setShouldStamp(1);
        numStamps++;
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
      
    public void setColorMap(Map<Double, String> colorMap) {
        this.colorMap = colorMap;
        updateView();
    }
    
    public void setImageMap(Map<Double, String> imageMap) {
        this.imageMap = imageMap;
        updateView();
    }
    
    public Map<Double, String> getColorMap() {
        return colorMap;
    }
    
    public Map<Double, String> getImageMap() {
        return imageMap;
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
        return penStatus ? 1 : 0;
    }

    public double getShowStatus () {
        return showStatus ? 1 : 0;
    }

    public boolean shouldClear () {
        return shouldClear;
    }
    
    public double clearScreen(double shouldClear) {
        double returnValue = home();
        setShouldClear(shouldClear);
        return returnValue;
    }
    
    public void setShouldClear(double shouldClear) {
        this.shouldClear = shouldClear == 1 ? true : false;
        updateView();
    }

    public double getPenColorIndex () {
        return penColorIndex;
    }

    public double setPenColorIndex (double[] penColorIndex) {
        this.penColorIndex = penColorIndex[0];
        updateView();
        return penColorIndex[0];
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
        return lineWidth;
    }

    public double setLineWidth (double[] lineWidth) {
        this.lineWidth = lineWidth[0];
        updateView();
        return lineWidth[0];
    }

    public boolean shouldStamp () {
        return shouldStamp;
    }

    public void setShouldStamp (double shouldStamp) {
        this.shouldStamp = shouldStamp == 1 ? true : false;
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

    public double setShouldClearStamp (double[] shouldClearStamp) {
        if(numStamps > 0) {
            numStamps = 0;
            this.shouldClearStamp = shouldClearStamp[0] == 1 ? true : false;
            updateView();
            return 1;
        }
        return 0;
    }
    
    public boolean isActive () {
        return isActive;
    }

    public void setActive (double isActive) {
        this.isActive = isActive == 1 ? true : false;
    }
  
    public void updateView() {
        setChanged();
        notifyObservers();
    }
    
}
