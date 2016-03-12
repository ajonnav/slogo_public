package command;

import java.util.List;
import model.IModelMap;


public class PenUpCommand extends Command {

    private IModelMap modelMap;

    public PenUpCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("penUp", null);
    }

}
