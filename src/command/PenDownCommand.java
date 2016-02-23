package command;
import model.TurtleModel;

public class PenDownCommand implements ICommand {

	private TurtleModel myTurtle;
	
	public PenDownCommand(TurtleModel turtle) {
		myTurtle = turtle;
	}

	@Override
	public void execute() {
		myTurtle.penDown();
	}

}
