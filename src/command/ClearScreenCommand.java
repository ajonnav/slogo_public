package command;

import java.util.List;
import model.IModelMap;


public class ClearScreenCommand extends Command {

    private IModelMap modelMap;

    public ClearScreenCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("clearScreen", null);
    }

}
