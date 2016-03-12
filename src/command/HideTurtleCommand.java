package command;

import java.util.List;
import model.IModelMap;


public class HideTurtleCommand extends Command {

    private IModelMap modelMap;

    public HideTurtleCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(0);
        setTakesUnlimitedParameters(false);
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("hide", null);
    }

}
