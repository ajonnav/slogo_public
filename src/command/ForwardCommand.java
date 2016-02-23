package command;

import model.TurtleModel;

public class ForwardCommand implements ICommand{
	private TurtleModel myTurtle;
	private double distance;
	public ForwardCommand(TurtleModel turtle, double dist) {
		myTurtle = turtle;
		distance = dist;
	}

	@Override
	public void execute() {
		myTurtle.forward(distance);	
	}
}
