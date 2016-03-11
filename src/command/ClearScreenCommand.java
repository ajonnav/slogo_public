package command;

import java.util.List;
import model.IModelMap;


public class ClearScreenCommand extends Command {

    private IModelMap modelMap;

    public ClearScreenCommand (IModelMap modelMap, List<String> text) {
        setNumChildren(0);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("clearScreen", null);
    }

}
