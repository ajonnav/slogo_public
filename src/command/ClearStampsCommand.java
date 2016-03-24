package command;

import java.util.List;
import model.IModelMap;

public class ClearStampsCommand extends Command {



    public ClearStampsCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().TurtleAction("clearStamps", null);
    }

}
