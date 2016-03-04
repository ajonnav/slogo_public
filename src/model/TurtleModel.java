package model;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import com.sun.prism.paint.Color;

import javafx.scene.image.ImageView;


public class TurtleModel extends Observable {
    
    private double turtleInitialX;
    private double turtleInitialY;
    private double turtleInitialHeading;
    private ImageView imageView;
    private PenModel pen;
    private boolean isActive;
    private double heading;
    private double positionX;
    private double positionY;
    private boolean showStatus;
    private double imageIndex;
    private Map<Double, String> colorMap;
    private Map<Double, String> imageMap;
    private List<LineModel> lineList;
    private List<StampModel> stampList;

    public TurtleModel (double turtleInitialX, double turtleInitialY, double turtleInitialHeading, 
                        Map<Double, String> colorMap, Map<Double, String> imageMap) {
        isActive = false;
        heading = turtleInitialHeading;
        this.turtleInitialHeading = turtleInitialHeading;
        positionX = turtleInitialX;
        this.turtleInitialX = turtleInitialX;
        positionY = turtleInitialY;
        this.turtleInitialY = turtleInitialY;
        showStatus = true;
        imageIndex = 5;
        double penColorIndex = 1;
        double lineWidth = 1;
        double style = 0;
        pen = new PenModel(false, lineWidth, penColorIndex, style);
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
    	if(pen.getStatus()) {
	    	lineList.add(new LineModel(positionX, positionY, 
	    			positionX+distance[0] * Math.cos(Math.toRadians(heading)), 
	    			positionY += distance[0] * Math.sin(Math.toRadians(heading)),
	    			pen.getSize(), pen.getColor(), pen.getStyle()));
    	}
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
        stampList.add(new StampModel(imageView, positionX, positionY));
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
        return pen.getColor();
    }
    

    public double setPenColorIndex (double[] penColorIndex) {
        this.pen.setColor(penColorIndex[0]);
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
    
  
    public void updateView() {
        setChanged();
        notifyObservers();
    }
    
}