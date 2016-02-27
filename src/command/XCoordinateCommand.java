package command;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.TurtleModel;

public class XCoordinateCommand implements ICommand {

	public static final int numChildren = 0;
	private Map<String, Observable> modelMap;
	private double x;
	
	public XCoordinateCommand(Map<String, Observable> modelMap, List<List<ICommand>> commands) {
		this.modelMap = modelMap;
		x = ((TurtleModel)modelMap.get("turtle")).getPositionX(); 
	}

	@Override
	public double execute() {
		return evaluate();
	}

	@Override
	public double evaluate() {
		// TODO Auto-generated method stub
		return x;
	}

}
