package command;

import java.util.List;
import model.ModelMap;

public class TurtlesCommand extends Command {

    private ModelMap modelMap;

    public TurtlesCommand (ModelMap modelMap, List<String> text) {
        this.modelMap = modelMap;
    }
    
    @Override
    public double execute () {
        return modelMap.getDisplay().getNumTurtles();
    }

}
