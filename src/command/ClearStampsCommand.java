package command;

import java.util.List;
import model.ModelMap;

public class ClearStampsCommand extends Command {

    private ModelMap modelMap;

    public ClearStampsCommand (ModelMap modelMap, List<String> text) {
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.TurtleAction("setShouldClearStamp", null);
    }

}
