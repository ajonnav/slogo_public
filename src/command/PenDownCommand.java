package command;

import java.util.List;
import model.ModelMap;


public class PenDownCommand extends Command {

    private ModelMap modelMap;

    public PenDownCommand (ModelMap modelMap, List<String> text) {
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.TurtleAction("penDown", null);
    }

}
