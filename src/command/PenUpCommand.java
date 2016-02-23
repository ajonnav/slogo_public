package command;
import model.TurtleModel;

public class PenUpCommand implements ICommand {

	private TurtleModel myTurtle;
	
	public PenUpCommand(TurtleModel turtle) {
		myTurtle = turtle;
	}

	@Override
	public void execute() {
		myTurtle.penUp();
	}

}
