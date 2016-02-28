package command;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import model.TurtleModel;

public class HomeCommand implements ICommand {

	public static final int numChildren = 0;
	private Map<String, Observable> modelMap;
	private double distance;
	
	public HomeCommand(Map<String, Observable> modelMap, List<List<ICommand>> commands) {
		this.modelMap = modelMap;
		TurtleModel turtleModel = (TurtleModel)modelMap.get("turtle");
        this.distance = Math.sqrt(Math.pow((0 - turtleModel.getPositionX()), 2) + Math.pow((0 - turtleModel.getPositionY()), 2));
	}

	@Override
	public double execute() {
		((TurtleModel)modelMap.get("turtle")).setPosition(0,0);
		return evaluate();
	}

	@Override
	public double evaluate() {
		// TODO Auto-generated method stub
		return distance;
	}

}
