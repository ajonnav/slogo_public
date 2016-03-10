package command;

import java.util.List;
import model.ModelMap;


public class StampCommand extends Command {

    private ModelMap modelMap;

    public StampCommand (ModelMap modelMap, List<String> text) {
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("stamp", null);
    }

}
