package command;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import model.TurtleModel;

public class PenUpCommand implements ICommand {

	private int numChildren = 0;
	private Map<String, Observable> modelMap;
	
	public PenUpCommand(Map<String, Observable> modelMap, List<List<ICommand>> commands) {
		this.modelMap = modelMap;
	}

	@Override
	public double execute() {
		((TurtleModel)modelMap.get("turtle")).penUp();
		return evaluate();
	}

	@Override
	public double evaluate() {
		return 0;
	}

}
