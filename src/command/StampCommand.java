package command;

import java.util.List;
import model.IModelMap;


public class StampCommand extends Command {


    public StampCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().TurtleAction("stamp", null);
    }

}
