package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.TurtleModel;


public class HomeCommand extends Command {

    private Map<String, Observable> modelMap;

    public HomeCommand (Map<String, Observable> modelMap, List<String> text) {
        setNumChildren(0);
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
