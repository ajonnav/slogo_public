package command;

import java.util.List;
import model.IModelMap;


public class ShowTurtleCommand extends Command {

    private IModelMap modelMap;

    public ShowTurtleCommand (IModelMap modelMap, List<String> text) {
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("show", null);
    }

}
