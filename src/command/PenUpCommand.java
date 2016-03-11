package command;

import java.util.List;
import model.IModelMap;


public class PenUpCommand extends Command {

    private IModelMap modelMap;

    public PenUpCommand (IModelMap modelMap, List<String> text) {
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("penUp", null);
    }

}
