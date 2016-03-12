package command;

import java.util.List;
import model.IModelMap;


public class StampCommand extends Command {

    private IModelMap modelMap;

    public StampCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("stamp", null);
    }

}
