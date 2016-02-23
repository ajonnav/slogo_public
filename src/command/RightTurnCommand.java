package command;

import model.TurtleModel;

public class RightTurnCommand implements ICommand{
	
	private TurtleModel myTurtle;
	double degrees;
	
	public RightTurnCommand(TurtleModel turtle, double degrees) {
		this.myTurtle = turtle;
		this.degrees = degrees;
	}
	
	public void execute() {
		myTurtle.turn(degrees);
	}

}