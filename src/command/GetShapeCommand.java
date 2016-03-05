package command;

import java.util.List;
import model.ModelMap;


public class GetShapeCommand extends Command {

    private ModelMap modelMap;

    public GetShapeCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.TurtleAction("getImageIndex", null);
    }
}
