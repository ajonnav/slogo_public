package command;

import java.util.List;
import model.IModelMap;


public class ClearScreenCommand extends Command {


    public ClearScreenCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().TurtleAction("clearScreen", null);
    }

}
