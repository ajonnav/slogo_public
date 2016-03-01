package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.TurtleModel;


public class HomeCommand implements ICommand {

    public static final int numChildren = 0;
    private Map<String, Observable> modelMap;

    public HomeCommand (Map<String, Observable> modelMap, List<List<ICommand>> commands) {
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        TurtleModel turtleModel = (TurtleModel) modelMap.get("turtle");
        turtleModel.setPosition(0, 0);
        return  Math.sqrt(Math.pow((0 - turtleModel.getPositionX()), 2) +
                          Math.pow((0 - turtleModel.getPositionY()), 2));
    }
}
