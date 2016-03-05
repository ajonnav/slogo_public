package command;

import java.util.List;
import model.ModelMap;


public class IsPenDownCommand extends Command {

    private ModelMap modelMap;

    public IsPenDownCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        setTakesUnlimitedParameters(false);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.TurtleAction("getPenStatus", null);
    }

}
