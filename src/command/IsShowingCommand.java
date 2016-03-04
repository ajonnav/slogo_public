package command;

import java.util.List;
import model.ModelMap;


public class IsShowingCommand extends Command {

    private ModelMap modelMap;

    public IsShowingCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.TurtleAction("getShowStatus", new double[]{-1});
    }

}
