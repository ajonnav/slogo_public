package command;

import java.util.List;
import model.IModelMap;


public class PenDownCommand extends Command {

    private IModelMap modelMap;

    public PenDownCommand (IModelMap modelMap, List<String> text) {
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("penDown", null);
    }

}
