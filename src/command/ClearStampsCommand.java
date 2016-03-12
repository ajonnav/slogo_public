package command;

import java.util.List;
import model.IModelMap;

public class ClearStampsCommand extends Command {



    public ClearStampsCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().TurtleAction("clearStamps", null);
    }

}
