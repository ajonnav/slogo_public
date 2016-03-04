package command;

import java.util.List;
import model.ModelMap;


public class GetPenColorCommand extends Command {

    private ModelMap modelMap;

    public GetPenColorCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.TurtleAction("getPenColorIndex", new double[]{-1});
    }
}
