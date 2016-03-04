package command;

import java.util.List;
import model.ModelMap;


public class HomeCommand extends Command {

    private ModelMap modelMap;

    public HomeCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("home", new double[]{-1});
    }
}
