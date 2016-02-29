package command;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.TurtleModel;

public class YCoordinateCommand implements ICommand {

	public static final int numChildren = 0;
	private double y;
	
	public YCoordinateCommand(Map<String, Observable> modelMap, List<List<ICommand>> commands) {
		y = ((TurtleModel)modelMap.get("turtle")).getPositionY(); 
	}

	@Override
	public double execute() {
		return evaluate();
	}

	@Override
	public double evaluate() {
		// TODO Auto-generated method stub
		return y;
	}
	
	@Override
    public int getNumChildren () {
        return numChildren;
    }

}
