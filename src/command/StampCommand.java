package command;

import java.util.List;
import model.IModelMap;


public class StampCommand extends Command {


    public StampCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().TurtleAction("stamp", null);
    }

}
