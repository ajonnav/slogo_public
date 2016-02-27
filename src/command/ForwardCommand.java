package command;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.TurtleModel;

public class ForwardCommand implements ICommand {

    public static int numChildren = 1;
    private Map<String, Observable> modelMap;
    private double distance;

    public ForwardCommand (Map<String, Observable> modelMap, List<List<ICommand>> commands) {
        this.modelMap = modelMap;
        this.distance = commands.get(0).get(0).evaluate();
    }

    @Override
    public double execute () {
        ((TurtleModel) modelMap.get("turtle")).forward(distance);
        return evaluate();
    }

    @Override
    public double evaluate () {
       return distance;
    }
}
