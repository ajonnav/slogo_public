package command;

import java.util.List;
import model.IModelMap;

public class ClearStampsCommand extends Command {

    private IModelMap modelMap;

    public ClearStampsCommand (IModelMap modelMap, List<String> text) {
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("clearStamps", null);
    }

}
