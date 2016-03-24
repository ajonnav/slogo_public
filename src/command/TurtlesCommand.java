package command;

import java.util.List;
import model.IModelMap;

public class TurtlesCommand extends Command {


    public TurtlesCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
    }
    
    @Override
    public double execute () {
        return getModelMap().getDisplay().getNumTurtles();
    }

}
