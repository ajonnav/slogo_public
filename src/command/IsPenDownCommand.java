package command;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.TurtleModel;

public class IsPenDownCommand implements ICommand {

	public static final int numChildren = 0;
	private boolean status;
	
	public IsPenDownCommand(Map<String, Observable> modelMap, List<List<ICommand>> commands) {
		status = ((TurtleModel)modelMap.get("turtle")).getPenStatus(); 
	}

	@Override
	public double execute() {
		return evaluate();
	}

	@Override
	public double evaluate() {
		// TODO Auto-generated method stub
		if(status) {
			return 1;
		}
		else {
			return 0;
		}
	}

}