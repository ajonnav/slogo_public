package command;

import java.util.List;
import model.IModelMap;


public class HomeCommand extends Command {

    private IModelMap modelMap;

    public HomeCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(0);
        setTakesUnlimitedParameters(false);
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("home", null);
    }
}
