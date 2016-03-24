package command;

import java.util.List;
import model.IModelMap;


public class PenUpCommand extends Command {


    public PenUpCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().TurtleAction("penUp", null);
    }

}
