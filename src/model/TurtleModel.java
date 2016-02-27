package model;
import java.util.Observable;

public class TurtleModel extends Observable{
	private double heading;
	private double positionX;
	private double positionY;
	private boolean penStatus;
	
	public TurtleModel(double turtleInitialX, double turtleInitialY, double turtleInitialHeading) {
		heading = turtleInitialHeading;
		positionX = turtleInitialX;
		positionY = turtleInitialY;
		penStatus = false;
		setChanged();
	}
	
	public void forward(double distance) {
		positionX+= distance*Math.cos(Math.toRadians(heading));
		positionY+= distance*Math.sin(Math.toRadians(heading));
		setChanged();
		notifyObservers();
	}
	
	public void turn(double degree) {
		heading+=degree;
		setChanged();
		notifyObservers();
	}
	
	public void penUp() {
		penStatus = false;
	}
	
	public void penDown() {
		penStatus = true;
	}
	
	public double getPositionY() {
		return positionY;
	}

	public double getPositionX() {
		return positionX;
	}

	public double getHeading() {
		return heading;
	}
	public boolean getPenStatus() {
		return penStatus;
	}
}