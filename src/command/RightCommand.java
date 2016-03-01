package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.TurtleModel;


public class RightCommand implements ICommand {

    public static int numChildren = 1;
    private Map<String, Observable> modelMap;
    private ICommand degrees;
    
    public RightCommand (Map<String, Observable> modelMap, List<List<ICommand>> commands) {
        this.modelMap = modelMap;
        this.degrees = commands.get(0).get(0);
    }

    public double execute () {
        double dist = degrees.execute();
        ((TurtleModel) modelMap.get("turtle")).turn(dist);
        return dist;
    }
}
