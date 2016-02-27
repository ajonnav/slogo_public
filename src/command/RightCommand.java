package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import model.TurtleModel;

public class RightCommand implements ICommand{
	
	public static int numChildren = 1;
    private Map<String, Observable> modelMap;
    private double degrees;
	
	public RightCommand(Map<String, Observable> modelMap, List<ICommand> commands) {
		this.modelMap = modelMap;
		this.degrees = commands.get(0).evaluate();
	}
	
	public double execute() {
		((TurtleModel) modelMap.get("turtle")).turn(degrees);
		return evaluate();
	}

	@Override
	public double evaluate() {
		// TODO Auto-generated method stub
		return degrees;
	}

}
