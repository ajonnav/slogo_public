package command;

import java.util.List;
import model.IModelMap;

public class ClearStampsCommand extends Command {

    private IModelMap modelMap;

    public ClearStampsCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("clearStamps", null);
    }

}
