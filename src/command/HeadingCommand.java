package command;

import java.util.List;
import model.ModelMap;


public class HeadingCommand extends Command {

    private ModelMap modelMap;

    public HeadingCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.TurtleAction("getHeading", new double[]{-1});
    }
}
