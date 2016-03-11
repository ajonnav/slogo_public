package command;

import java.util.List;
import model.IModelMap;

public class TurtlesCommand extends Command {

    private IModelMap modelMap;

    public TurtlesCommand (IModelMap modelMap, List<String> text) {
        this.modelMap = modelMap;
    }
    
    @Override
    public double execute () {
        return modelMap.getDisplay().getNumTurtles();
    }

}
