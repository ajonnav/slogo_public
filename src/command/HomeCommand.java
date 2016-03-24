package command;

import java.util.List;
import model.IModelMap;


public class HomeCommand extends Command {


    public HomeCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
        setNumChildren(0);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().TurtleAction("home", null);
    }
}
