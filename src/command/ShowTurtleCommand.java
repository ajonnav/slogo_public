package command;

import java.util.List;
import model.IModelMap;


public class ShowTurtleCommand extends Command {


    public ShowTurtleCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().TurtleAction("show", null);
    }

}
