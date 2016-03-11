package command;

import java.util.List;
import model.IModelMap;


public class IsShowingCommand extends Command {

    private IModelMap modelMap;

    public IsShowingCommand (IModelMap modelMap, List<String> text) {
        setNumChildren(0);
        setTakesUnlimitedParameters(false);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("getShowStatus", null);
    }

}
