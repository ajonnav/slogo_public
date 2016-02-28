package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import model.TurtleModel;

public class LeftCommand implements ICommand{

	public static final int numChildren = 1;
	private Map<String, Observable> modelMap;
    private double degrees;
	
	public LeftCommand(Map<String, Observable> modelMap, List<List<ICommand>> commands) {
		this.modelMap = modelMap;
		degrees = commands.get(0).get(0).evaluate();
	}

	@Override
	public double execute() {
		((TurtleModel) modelMap.get("turtle")).turn(-degrees);
		return evaluate();
	}

	@Override
	public double evaluate() {
		// TODO Auto-generated method stub
		return degrees;
	}

}
