package command;

import java.util.List;
import model.IModelMap;


public class PenDownCommand extends Command {


    public PenDownCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().TurtleAction("penDown", null);
    }

}
