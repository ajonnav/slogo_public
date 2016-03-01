package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.TurtleModel;


public class ForwardCommand implements ICommand {

    public static final int numChildren = 1;
    private Map<String, Observable> modelMap;
    private ICommand distance;

    public ForwardCommand (Map<String, Observable> modelMap, List<List<ICommand>> commands) {
        this.modelMap = modelMap;
        this.distance = commands.get(0).get(0);
    }

    @Override
    public double execute () {
        double dist = distance.execute();
        ((TurtleModel) modelMap.get("turtle")).forward(dist);
        return dist;
    }
}
