package command;

import java.util.List;
import model.IModelMap;


public class GetPenColorCommand extends Command {

    private IModelMap modelMap;

    public GetPenColorCommand (IModelMap modelMap, List<String> text) {
        setNumChildren(0);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("getPenColorIndex", null);
    }
}
