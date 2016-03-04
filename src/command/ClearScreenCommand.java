package command;

import java.util.List;
import model.ModelMap;


public class ClearScreenCommand extends Command {

    private ModelMap modelMap;

    public ClearScreenCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("clearScreen", new double[]{1});
    }

}
