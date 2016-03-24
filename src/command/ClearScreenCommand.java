package command;

import java.util.List;
import model.IModelMap;


public class ClearScreenCommand extends Command {


    public ClearScreenCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().TurtleAction("clearScreen", null);
    }

}
