package command;

import java.util.List;
import model.IModelMap;


public class HomeCommand extends Command {


    public HomeCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(0);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().TurtleAction("home", null);
    }
}
