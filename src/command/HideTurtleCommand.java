package command;

import java.util.List;
import model.ModelMap;


public class HideTurtleCommand extends Command {

    private ModelMap modelMap;

    public HideTurtleCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.TurtleAction("hide", new double[]{-1});
    }

}
