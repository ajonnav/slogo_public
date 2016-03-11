package command;

import java.util.List;
import model.IModelMap;


public class HeadingCommand extends Command {

    private IModelMap modelMap;

    public HeadingCommand (IModelMap modelMap, List<String> text) {
        setNumChildren(0);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("getHeading", null);
    }
}
