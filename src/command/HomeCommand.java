package command;

import java.util.List;
import model.ModelMap;
import model.TurtleModel;


public class HomeCommand extends Command {

    private ModelMap modelMap;

    public HomeCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        TurtleModel turtleModel = modelMap.getTurtle();
        turtleModel.setPosition(0, 0);
        return  Math.sqrt(Math.pow((0 - turtleModel.getPositionX()), 2) +
                          Math.pow((0 - turtleModel.getPositionY()), 2));
    }
}
