package command;

import java.util.List;
import model.IModelMap;


public class StampCommand extends Command {

    private IModelMap modelMap;

    public StampCommand (IModelMap modelMap, List<String> text) {
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("stamp", null);
    }

}
