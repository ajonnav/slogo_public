package command;

import java.util.List;
import model.IModelMap;


public class HideTurtleCommand extends Command {

    private IModelMap modelMap;

    public HideTurtleCommand (IModelMap modelMap, List<String> text) {
        setNumChildren(0);
        setTakesUnlimitedParameters(false);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("hide", null);
    }

}
