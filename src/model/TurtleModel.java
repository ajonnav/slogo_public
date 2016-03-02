package model;

import java.util.Observable;


public class TurtleModel extends Observable {
    private double heading;
    private double positionX;
    private double positionY;
    private boolean penStatus;
    private boolean showStatus;
    private boolean shouldClear;
    private final double initX;
    private final double initY;

    public TurtleModel (double turtleInitialX, double turtleInitialY, double turtleInitialHeading) {
        heading = turtleInitialHeading;
        positionX = turtleInitialX;
        positionY = turtleInitialY;
        initX = turtleInitialX;
        initY = turtleInitialY;
        penStatus = false;
        showStatus = true;
        setChanged();
    }

    public void forward (double distance) {
        positionX += distance * Math.cos(Math.toRadians(heading));
        positionY += distance * Math.sin(Math.toRadians(heading));
        setChanged();
        notifyObservers();
    }

    public void setHeading (double degrees) {
        heading = degrees;
        setChanged();
        notifyObservers();
    }

    public void setPosition (double x, double y) {
        positionX = x;
        positionY = y;
        setChanged();
        notifyObservers();
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
        setChanged();
        notifyObservers();
    }

    public void hide () {
        showStatus = false;
        setChanged();
        notifyObservers();
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
    
    public double getInitX() {
        return initX;
    }
    
    public double getInitY() {
        return initY;
    }

    public boolean getPenStatus () {
        return penStatus;
    }

    public boolean getShowStatus () {
        return showStatus;
    }

	public boolean shouldClear() {
		return shouldClear;
	}

	public void setShouldClear(boolean shouldClear) {
		this.shouldClear = shouldClear;
		setChanged();
        notifyObservers();
	}
}
